package com.val.example.axon3framework.eventhandlers;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventHandler {
    private static final Logger log = LoggerFactory.getLogger(LoggingEventHandler.class);

    @EventHandler
    public void on(Object event) {
        log.info(String.format("Hey we received an event: %s", event.toString()));
    }
}
