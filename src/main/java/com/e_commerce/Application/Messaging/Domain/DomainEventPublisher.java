package com.e_commerce.Application.Messaging.Domain;

import java.util.List;

public interface DomainEventPublisher {
    void publish(List<Object> events);
}
