package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot

@TaskerInputRoot
class EventUpdate @JvmOverloads constructor(
    @field:TaskerInputField("update") var update: String = "",
)
