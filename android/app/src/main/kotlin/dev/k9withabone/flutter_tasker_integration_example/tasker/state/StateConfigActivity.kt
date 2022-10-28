package dev.k9withabone.flutter_tasker_integration_example.tasker.state

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import io.flutter.embedding.android.FlutterActivity

class StateHelper(config: TaskerPluginConfig<StateInput>) :
    TaskerPluginConfigHelper<StateInput, StateOutput, StateRunner>(config) {
    override val runnerClass = StateRunner::class.java
    override val inputClass = StateInput::class.java
    override val outputClass = StateOutput::class.java
}

class StateConfigActivity : FlutterActivity(), TaskerPluginConfig<StateInput>,
    TaskerStateConfigApi {
    private var config: String = ""

    private val stateHelper by lazy { StateHelper(this) }

    @Suppress("ACCIDENTAL_OVERRIDE")
    override val context: Context
        get() = super.getContext()

    override val inputForTasker: TaskerInput<StateInput>
        get() = TaskerInput(StateInput(config))

    override fun assignFromInput(input: TaskerInput<StateInput>) {
        config = input.regular.config
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskerStateConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, this)
        stateHelper.onCreate()
    }

    override fun getDartEntrypointFunctionName(): String = "taskerStateConfigMain"

    override fun getDartEntrypointArgs(): MutableList<String> = mutableListOf(config)

    override fun configDone(input: TaskerStateInput, callback: (Boolean) -> Unit) {
        config = input.config ?: ""
        callback(true)
        stateHelper.finishForTasker()
    }

    override fun onDestroy() {
        TaskerStateConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, null)
        super.onDestroy()
    }
}