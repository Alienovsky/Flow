package com.kamilmade;

import com.kamilmade.enums.Events;
import com.kamilmade.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class ConfigEnums extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

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
                    .source(States.INITIAL).target(States.REGISTERED)
                    .event(Events.RUN)
                .and()
                .withExternal()
                    .source(States.CANCELLED).target(States.FINAL)
                    .event(Events.FINISH)
                .and()
                .withExternal()
                    .source(States.JOINED).target(States.FINAL)
                    .event(Events.FINISH)
                .and()
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

    private static final class StateMachineListener extends StateMachineListenerAdapter<States, Events> {
        @Override
        public void stateChanged(State<States, Events> from, State<States, Events> to) {
            System.out.println("Order state changed to " + to.getId());
        }
    }


}
