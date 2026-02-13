package com.e_commerce.Infrastructure.Messaging;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.e_commerce.Application.Messaging.Domain.DomainEventPublisher;

@Component
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public DomainEventPublisherImpl(
        ApplicationEventPublisher publisher
    ) {
        this.publisher = publisher;
    }
    
    @Override
    public void publish(List<Object> events) {
        events.forEach(publisher::publishEvent);
    }
    
}
