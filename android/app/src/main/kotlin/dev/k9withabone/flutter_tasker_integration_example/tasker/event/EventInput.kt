package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import dev.k9withabone.flutter_tasker_integration_example.R

@TaskerInputRoot
class EventInput @JvmOverloads constructor(
    @field:TaskerInputField("time", R.string.time) var time: String = "",
)
