package kg.alfit.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException{

    public KafkaProducerException(String message) {
        super(message);
    }
}
