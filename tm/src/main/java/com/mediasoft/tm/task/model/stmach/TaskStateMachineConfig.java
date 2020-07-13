package com.mediasoft.tm.task.model.stmach;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
public class TaskStateMachineConfig extends StateMachineConfigurerAdapter<TaskState, TaskEvent> {

    @Override
    public void configure(StateMachineTransitionConfigurer<TaskState, TaskEvent> transitions) throws Exception {
        transitions
                .withExternal().source(TaskState.NEW).target(TaskState.IN_PROCESS).event(TaskEvent.TAKE_IN_PROCESS)
                .and()
                .withExternal().source(TaskState.IN_PROCESS).target(TaskState.IN_CHECK).event(TaskEvent.TO_CHECK)
                .and()
                .withExternal().source(TaskState.IN_CHECK).target(TaskState.FINISHED).event(TaskEvent.APPROVE)
                .and()
                .withExternal().source(TaskState.IN_CHECK).target(TaskState.IN_PROCESS).event(TaskEvent.DISAPPROVE);
    }

    @Override
    public void configure(StateMachineStateConfigurer<TaskState, TaskEvent> states) throws Exception {
        states.withStates()
                .initial(TaskState.NEW)
                .state(TaskState.IN_PROCESS)
                .state(TaskState.IN_CHECK)
                .end(TaskState.FINISHED);
    }
}
