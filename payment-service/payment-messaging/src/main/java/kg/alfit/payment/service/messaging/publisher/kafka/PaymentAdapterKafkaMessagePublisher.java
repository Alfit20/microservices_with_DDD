package kg.alfit.payment.service.messaging.publisher.kafka;

import kg.alfit.kafka.order.avro.model.PaymentResponseAvroModel;
import kg.alfit.kafka.producer.helper.KafkaMessageHelper;
import kg.alfit.kafka.producer.service.KafkaProducer;
import kg.alfit.payment.service.domain.config.PaymentServiceConfigData;
import kg.alfit.payment.service.domain.event.PaymentEvent;
import kg.alfit.payment.service.messaging.mapper.PaymentMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentAdapterKafkaMessagePublisher {

    public void publish(
            PaymentEvent domainEvent, PaymentMessagingDataMapper paymentMessagingDataMapper,
            KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer,
            PaymentServiceConfigData paymentServiceConfigData,
            String orderId,
            KafkaMessageHelper kafkaMessageHelper) {
        try {
            PaymentResponseAvroModel paymentResponseAvroModel =
                    paymentMessagingDataMapper.paymentEventToPaymentResponseAvroModel(domainEvent);
            kafkaProducer.send(paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderId,
                            "PaymentResponseAvroModel"));
            log.info("PaymentResponseAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error(
                    """
                            Error while sending PaymentResponseAvroModel message to kafka
                            with order id: {}, error: {}
                            """, orderId, e.getMessage());
        }
    }
}
