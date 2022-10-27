package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import dev.k9withabone.flutter_tasker_integration_example.tasker.action.TaskerActionConfigApi
import io.flutter.embedding.android.FlutterActivity

class EventHelper(config: TaskerPluginConfig<EventInput>) : TaskerPluginConfigHelper<EventInput, EventOutput, EventRunner>(config) {
    override val runnerClass = EventRunner::class.java
    override val inputClass = EventInput::class.java
    override val outputClass = EventOutput::class.java
}

class EventConfigActivity : FlutterActivity(), TaskerPluginConfig<EventInput>, TaskerEventConfigApi {
    private var time: String = ""

    private val eventHelper by lazy { EventHelper(this) }

    @Suppress("ACCIDENTAL_OVERRIDE")
    override val context: Context
        get() = super.getContext()

    override val inputForTasker: TaskerInput<EventInput>
        get() = TaskerInput(EventInput(time))

    override fun assignFromInput(input: TaskerInput<EventInput>) {
        time = input.regular.time
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskerEventConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, this)
        eventHelper.onCreate()
    }

    override fun getDartEntrypointFunctionName(): String = "taskerEventConfigMain"

    override fun getDartEntrypointArgs(): MutableList<String> = mutableListOf(time)

    override fun configDone(input: TaskerEventInput, callback: (Boolean) -> Unit) {
        time = input.time ?: ""
        callback(true)
        eventHelper.finishForTasker()
    }

    override fun onDestroy() {
        TaskerActionConfigApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, null)
        super.onDestroy()
    }
}