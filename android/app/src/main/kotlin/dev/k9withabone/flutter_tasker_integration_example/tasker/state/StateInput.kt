package dev.k9withabone.flutter_tasker_integration_example.tasker.state

import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import dev.k9withabone.flutter_tasker_integration_example.R

@TaskerInputRoot
class StateInput @JvmOverloads constructor(
    @field:TaskerInputField("config", R.string.config) var config: String = "",
)
