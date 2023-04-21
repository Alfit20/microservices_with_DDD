package kg.alfit.domain.event;

public interface DomainEvent<T> {
    void fire();
}
