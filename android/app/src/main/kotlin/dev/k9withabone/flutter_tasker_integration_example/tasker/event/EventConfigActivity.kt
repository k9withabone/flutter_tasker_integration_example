package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import io.flutter.embedding.android.FlutterActivity

class EventHelper(config: TaskerPluginConfig<EventInput>) :
    TaskerPluginConfigHelper<EventInput, EventOutput, EventRunner>(config) {
    override val runnerClass = EventRunner::class.java
    override val inputClass = EventInput::class.java
    override val outputClass = EventOutput::class.java
}

class EventConfigActivity : FlutterActivity(), TaskerPluginConfig<EventInput>,
    TaskerEventConfigApi {
    private var config: String = ""

    private val eventHelper by lazy { EventHelper(this) }

    @Suppress("ACCIDENTAL_OVERRIDE")
    override val context: Context
        get() = super.getContext()

    override val inputForTasker: TaskerInput<EventInput>
        get() = TaskerInput(EventInput(config))

    override fun assignFromInput(input: TaskerInput<EventInput>) {
        config = input.regular.config
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskerEventConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, this)
        eventHelper.onCreate()
    }

    override fun getDartEntrypointFunctionName(): String = "taskerEventConfigMain"

    override fun getDartEntrypointArgs(): MutableList<String> = mutableListOf(config)

    override fun configDone(input: TaskerEventInput, callback: (Boolean) -> Unit) {
        config = input.config ?: ""
        callback(true)
        eventHelper.finishForTasker()
    }

    override fun onDestroy() {
        TaskerEventConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, null)
        super.onDestroy()
    }
}