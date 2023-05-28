package kg.alfit.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
