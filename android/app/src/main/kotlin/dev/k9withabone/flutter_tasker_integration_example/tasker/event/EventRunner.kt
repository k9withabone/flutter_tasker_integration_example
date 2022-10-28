package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import android.content.Context
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionEvent
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EventRunner : TaskerPluginRunnerConditionEvent<EventInput, EventOutput, EventUpdate>() {
    private class EventRunApi : TaskerEventRunApi {
        val flutterDoneCompletable = CompletableDeferred<TaskerEventOutput>()

        override fun runDone(output: TaskerEventOutput, callback: (Boolean) -> Unit) {
            callback(true)
            flutterDoneCompletable.complete(output)
        }
    }

    private var result = EventOutput("")

    override fun getSatisfiedCondition(
        context: Context,
        input: TaskerInput<EventInput>,
        update: EventUpdate?
    ): TaskerPluginResultCondition<EventOutput> {
        Looper.prepare()
        val runnerHandler = HandlerCompat.createAsync(Looper.myLooper()!!)

        HandlerCompat.createAsync(Looper.getMainLooper()).post {
            val eventRunApi = EventRunApi()
            val engine = FlutterEngine(context)
            val dartBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath()
            val entrypoint = DartExecutor.DartEntrypoint(dartBundlePath, "taskerEventRunMain")

            TaskerEventRunApi.setUp(engine.dartExecutor.binaryMessenger, eventRunApi)
            engine.dartExecutor.executeDartEntrypoint(
                entrypoint,
                listOf(input.regular.config, update?.update ?: "")
            )

            var runResult: TaskerEventOutput? = null
            MainScope().launch {
                runResult = eventRunApi.flutterDoneCompletable.await()
            }.invokeOnCompletion {
                engine.destroy()
                runnerHandler.post {
                    result = EventOutput(runResult?.output ?: "")
                    runnerHandler.looper.quit()
                }
            }
        }

        Looper.loop()
        return TaskerPluginResultConditionSatisfied(context, result)
    }
}
