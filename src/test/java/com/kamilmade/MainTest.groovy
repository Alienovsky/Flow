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

    def "test move state from Registered to Cancelled"(){
        given:
        stateMachine.start();

        when:
        stateMachine.sendEvent(Events.CANCEL);

        then:
        stateMachine.getState() == States.CANCELLED;

    }
}
