package com.e_commerce.Core.Events;

import java.time.Instant;

public interface DomainEvent {
    Instant occuredOn();
}
