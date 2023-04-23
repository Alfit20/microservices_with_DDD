package kg.alfit.payment.service.dataaccess.payment.adapter;

import kg.alfit.payment.service.dataaccess.payment.mapper.PaymentDataAccessMapper;
import kg.alfit.payment.service.dataaccess.payment.repository.PaymentJpaRepository;
import kg.alfit.payment.service.domain.entity.Payment;
import kg.alfit.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper.paymentEntityToPayment(
                paymentJpaRepository.save(paymentDataAccessMapper.paymentToPaymentEntity(payment))
        );
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}
