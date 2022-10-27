package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import dev.k9withabone.flutter_tasker_integration_example.R

@TaskerOutputObject()
class EventOutput(
    @get:TaskerOutputVariable(
        "time",
        R.string.time,
        R.string.time_description
    ) val time: String
)
