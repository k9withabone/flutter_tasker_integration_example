package dev.k9withabone.flutter_tasker_integration_example.tasker.action

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import io.flutter.embedding.android.FlutterActivity

class ActionHelper(config: TaskerPluginConfig<ActionInput>) :
    TaskerPluginConfigHelper<ActionInput, ActionOutput, ActionRunner>(config) {
    override val runnerClass: Class<ActionRunner> get() = ActionRunner::class.java
    override val inputClass: Class<ActionInput> get() = ActionInput::class.java
    override val outputClass: Class<ActionOutput> = ActionOutput::class.java
}

class ActionConfigActivity : FlutterActivity(), TaskerPluginConfig<ActionInput>,
    TaskerActionConfigApi {
    private var config: String = ""

    private val taskerHelper by lazy { ActionHelper(this) }

    @Suppress("ACCIDENTAL_OVERRIDE")
    override val context: Context
        get() = super.getContext()

    override val inputForTasker: TaskerInput<ActionInput>
        get() = TaskerInput(ActionInput(config))

    override fun assignFromInput(input: TaskerInput<ActionInput>) {
        config = input.regular.config
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskerActionConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, this)
        taskerHelper.onCreate()
    }

    override fun getDartEntrypointFunctionName(): String = "taskerActionConfigMain"

    override fun getDartEntrypointArgs(): MutableList<String> = mutableListOf(config)

    override fun configDone(input: TaskerActionInput, callback: (Boolean) -> Unit) {
        config = input.config ?: ""
        callback(true)
        taskerHelper.finishForTasker()
    }

    override fun onDestroy() {
        TaskerActionConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, null)
        super.onDestroy()
    }
}