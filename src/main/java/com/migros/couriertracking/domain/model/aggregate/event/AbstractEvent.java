package com.migros.couriertracking.domain.model.aggregate.event;

import org.springframework.context.ApplicationEvent;

public abstract class AbstractEvent extends ApplicationEvent {

    protected AbstractEvent() {
        super("");
    }
}