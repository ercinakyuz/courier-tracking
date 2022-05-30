package com.migros.couriertracking.domain.model.aggregate.ofwork.interceptor;

import com.migros.couriertracking.domain.model.aggregate.AggregateRoot;
import com.migros.couriertracking.domain.model.aggregate.event.AbstractEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class AggregateOfWorkInterceptor {

    private final ApplicationEventPublisher applicationEventPublisher;

    @AfterReturning(returning = "aggregate", pointcut = "execution(* com.migros.couriertracking.domain.model.aggregate.ofwork.AggregateOfWork+.*(..))")
    public void process(final AggregateRoot aggregate) {
        for (final AbstractEvent event : aggregate.getEvents()) {
            applicationEventPublisher.publishEvent(event);
        }
        aggregate.clearEvents();
    }
}
