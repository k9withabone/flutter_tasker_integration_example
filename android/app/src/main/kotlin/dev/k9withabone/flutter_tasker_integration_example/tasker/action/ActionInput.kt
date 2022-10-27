package dev.k9withabone.flutter_tasker_integration_example.tasker.action

import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import dev.k9withabone.flutter_tasker_integration_example.R

@TaskerInputRoot
class ActionInput @JvmOverloads constructor(
    @field:TaskerInputField("input", R.string.config) var config: String = "",
)
