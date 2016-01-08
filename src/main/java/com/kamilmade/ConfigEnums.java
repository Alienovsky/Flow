package com.kamilmade;

import com.kamilmade.enums.Events;
import com.kamilmade.enums.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class ConfigEnums extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.INITIAL)
                .end(States.FINAL)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(States.REGISTERED).target(States.CANCELLED)
                    .event(Events.CANCEL)
                .and()
                .withExternal()
                    .source(States.REGISTERED).target(States.JOINED)
                    .event(Events.ONBOARDED)
                .and()
                .withInternal()
                    .source(States.REGISTERED)
                    .event(Events.DATA_CHANGED);
    }
}
