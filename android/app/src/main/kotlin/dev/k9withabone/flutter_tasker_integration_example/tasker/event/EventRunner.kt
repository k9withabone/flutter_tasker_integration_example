package dev.k9withabone.flutter_tasker_integration_example.tasker.event

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionEvent
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition

class EventRunner : TaskerPluginRunnerConditionEvent<EventInput, EventOutput, EventUpdate>() {
    override fun getSatisfiedCondition(
        context: Context,
        input: TaskerInput<EventInput>,
        update: EventUpdate?
    ): TaskerPluginResultCondition<EventOutput> {
        TODO("Not yet implemented")
    }
}
