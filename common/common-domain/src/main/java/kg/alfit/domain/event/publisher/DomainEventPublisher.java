package kg.alfit.domain.event.publisher;

import kg.alfit.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

    void publish(T domainEvent);
}
