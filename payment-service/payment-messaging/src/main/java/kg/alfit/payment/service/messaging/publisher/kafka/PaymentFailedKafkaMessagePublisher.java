package kg.alfit.payment.service.messaging.publisher.kafka;

import kg.alfit.kafka.order.avro.model.PaymentResponseAvroModel;
import kg.alfit.kafka.producer.helper.KafkaMessageHelper;
import kg.alfit.kafka.producer.service.KafkaProducer;
import kg.alfit.payment.service.domain.config.PaymentServiceConfigData;
import kg.alfit.payment.service.domain.event.PaymentCompletedEvent;
import kg.alfit.payment.service.domain.event.PaymentFailedEvent;
import kg.alfit.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import kg.alfit.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher;
import kg.alfit.payment.service.messaging.mapper.PaymentMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedKafkaMessagePublisher implements PaymentFailedMessagePublisher {
    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;
    private final PaymentAdapterKafkaMessagePublisher paymentAdapterKafkaMessagePublisher;


    @Override
    public void publish(PaymentFailedEvent domainEvent) {
        String orderId = domainEvent.getPayment().getOrderId().getValue().toString();
        log.info("Received PaymentFailedEvent for order id: {}", orderId);
        paymentAdapterKafkaMessagePublisher.publish(domainEvent,
                paymentMessagingDataMapper, kafkaProducer,
                paymentServiceConfigData,
                orderId, kafkaMessageHelper);
    }

}
