package dev.k9withabone.flutter_tasker_integration_example.tasker.state

import android.content.Context
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionState
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionUnsatisfied
import io.flutter.FlutterInjector
import io.flutter.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StateRunner : TaskerPluginRunnerConditionState<StateInput, StateOutput>() {
    companion object {
        private var isOn = false

        private fun run(context: Context) {
            StateConfigActivity::class.java.requestQuery(context)
        }

        fun off(context: Context) {
            isOn = false
            run(context)
        }

        fun on(context: Context) {
            isOn = true
            run(context)
        }

        fun toggle(context: Context) {
            isOn = !isOn
            run(context)
        }
    }

    private class StateRunApi : TaskerStateRunApi {
        val flutterDoneCompletable = CompletableDeferred<TaskerStateOutput>()

        override fun runDone(output: TaskerStateOutput, callback: (Boolean) -> Unit) {
            callback(true)
            flutterDoneCompletable.complete(output)
        }
    }

    private var resultCompletable = CompletableDeferred<StateOutput>()

    override fun getSatisfiedCondition(
        context: Context,
        input: TaskerInput<StateInput>,
        update: Unit?
    ): TaskerPluginResultCondition<StateOutput> {
//        var looper = Looper.myLooper()
//        if (looper == null) {
//            Looper.prepare()
//            looper = Looper.myLooper()
//        }
        val runnerHandler = HandlerCompat.createAsync(Looper.myLooper()!!)

        val posted = HandlerCompat.createAsync(Looper.getMainLooper()).postAtFrontOfQueue {
            Log.d("tasker", "starting engine set up on main")
            val stateRunApi = StateRunApi()
            val engine = FlutterEngine(context)
            val dartBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath()
            val entrypoint = DartExecutor.DartEntrypoint(dartBundlePath, "taskerStateRunMain")

            TaskerStateRunApi.setUp(engine.dartExecutor.binaryMessenger, stateRunApi)
            Log.d("tasker", "executing dart")
            engine.dartExecutor.executeDartEntrypoint(
                entrypoint,
                listOf(input.regular.config, isOn.toString())
            )

            var runResult: TaskerStateOutput? = null
            Log.d("tasker", "launching wait for result")
            MainScope().launch {
                runResult = stateRunApi.flutterDoneCompletable.await()
            }.invokeOnCompletion {
                engine.destroy()
                runnerHandler.post {
                    resultCompletable.complete(StateOutput(runResult?.output ?: ""))
                    Log.d("tasker", "result completed")
//                    runnerHandler.looper.quit()
                }
            }
        }
        Log.d("tasker", "Posted: $posted")

//        Looper.loop()
        val result: StateOutput
        Log.d("tasker", "runBlocking")
        runBlocking {
            result = resultCompletable.await()
        }
        Log.d("tasker", "runBlocking done")
        if (isOn) {
            return TaskerPluginResultConditionSatisfied(context, result)
        }
        return TaskerPluginResultConditionUnsatisfied()
    }
}
