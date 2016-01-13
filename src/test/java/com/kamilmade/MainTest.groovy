package com.kamilmade

import com.kamilmade.enums.Events
import com.kamilmade.enums.States
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration;
import org.springframework.statemachine.StateMachine
import spock.lang.Specification

@ContextConfiguration(classes = ConfigEnums)
class MainTest extends Specification {
    @Autowired
    StateMachine<States,Events> stateMachine;



    def "test start state machine and move from technical INITIAL state to REGISTERED"(){
        when:
        stateMachine.start();

        then:
        stateMachine.getState().getId() == States.INITIAL;

        when:
        stateMachine.sendEvent(Events.RUN);

        then:
        stateMachine.getState().getId() == States.REGISTERED;
    }

    def "test move state from REGISTERED to CANCELLED"(){
        given:
        stateMachine.start();
        stateMachine.sendEvent(Events.RUN)

        when:
        stateMachine.sendEvent(Events.CANCEL);

        then:
        stateMachine.getState().getId() == States.CANCELLED;

        when:
        stateMachine.sendEvent(Events.FINISH);

        then:
        stateMachine.getState().getId() == States.FINAL;
    }


}
