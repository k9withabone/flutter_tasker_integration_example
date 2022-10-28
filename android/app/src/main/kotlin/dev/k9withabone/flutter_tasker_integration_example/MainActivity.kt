package dev.k9withabone.flutter_tasker_integration_example

import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import dev.k9withabone.flutter_tasker_integration_example.tasker.event.EventConfigActivity
import dev.k9withabone.flutter_tasker_integration_example.tasker.event.EventUpdate
import dev.k9withabone.flutter_tasker_integration_example.tasker.event.TaskerEventTriggerApi
import dev.k9withabone.flutter_tasker_integration_example.tasker.event.TaskerEventUpdate
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity(), TaskerEventTriggerApi {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskerEventTriggerApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, this)
    }

    override fun triggerEvent(update: TaskerEventUpdate, callback: (Boolean) -> Unit) {
        callback(true)
        EventConfigActivity::class.java.requestQuery(context, EventUpdate(update.update ?: ""))
    }

    override fun onDestroy() {
        TaskerEventTriggerApi.setUp(flutterEngine!!.dartExecutor.binaryMessenger, null)
        super.onDestroy()
    }
}
