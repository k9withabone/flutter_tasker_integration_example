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
            Log.d("tasker", "runDone")
            callback(true)
            flutterDoneCompletable.complete(output)
            Log.d("tasker", "flutterDoneCompletable completed")
        }
    }

    override fun getSatisfiedCondition(
        context: Context,
        input: TaskerInput<StateInput>,
        update: Unit?
    ): TaskerPluginResultCondition<StateOutput> = runBlocking {
        Log.d("tasker", "starting engine set up")
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

        Log.d("tasker", "awaiting result")
        val runResult = stateRunApi.flutterDoneCompletable.await()
        Log.d("tasker", "result received")
        val result = StateOutput(runResult.output ?: "")
        if (isOn) {
            return@runBlocking TaskerPluginResultConditionSatisfied(context, result)
        }
        return@runBlocking TaskerPluginResultConditionUnsatisfied()
    }
}
