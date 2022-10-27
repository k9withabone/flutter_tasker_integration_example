package dev.k9withabone.flutter_tasker_integration_example.tasker.action

import android.content.Context
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ActionRunner : TaskerPluginRunnerAction<ActionInput, ActionOutput>() {
    private class ActionRunApi : TaskerActionRunApi {
        val flutterDoneCompletable = CompletableDeferred<TaskerActionOutput>()

        override fun runDone(output: TaskerActionOutput, callback: (Boolean) -> Unit) {
            callback(true)
            flutterDoneCompletable.complete(output)
        }
    }

    private var result = ActionOutput("")

    override fun run(
        context: Context,
        input: TaskerInput<ActionInput>
    ): TaskerPluginResult<ActionOutput> {
        Looper.prepare()
        val runnerHandler = HandlerCompat.createAsync(Looper.myLooper()!!)

        HandlerCompat.createAsync(Looper.getMainLooper()).post {
            val actionRunApi = ActionRunApi()
            val engine = FlutterEngine(context)
            val dartBundlePath = FlutterInjector.instance().flutterLoader().findAppBundlePath()
            val entrypoint = DartExecutor.DartEntrypoint(dartBundlePath, "taskerActionRunMain")

            TaskerActionRunApi.setUp(engine.dartExecutor.binaryMessenger, actionRunApi)
            engine.dartExecutor.executeDartEntrypoint(entrypoint, listOf(input.regular.config))

            var runResult = TaskerActionOutput()
            MainScope().launch {
                runResult = actionRunApi.flutterDoneCompletable.await()
            }.invokeOnCompletion {
                engine.destroy()
                runnerHandler.post {
                    result = ActionOutput(runResult.config ?: "")
                    runnerHandler.looper.quit()
                }
            }
        }

        Looper.loop()
        return TaskerPluginResultSucess(result)
    }
}
