package joao.pedro.productsapi.infrastructure.gateways.payment;

import joao.pedro.productsapi.entity.payment.gateway.PaymentGateway;
import joao.pedro.productsapi.entity.payment.model.Payment;
import joao.pedro.productsapi.infrastructure.config.db.repository.PaymentRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentDatabaseGateway implements PaymentGateway {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment create(Payment payment) {
        paymentRepository.save(payment.toPaymentEntity());
        return payment;
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentRepository.findById(id).map(PaymentEntity::toPayment);
    }
}
