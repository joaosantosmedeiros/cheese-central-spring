package joao.pedro.productsapi.entity.payment.gateway;

import joao.pedro.productsapi.entity.payment.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentGateway {
    Payment create(Payment payment);
    Optional<Payment> findById(UUID id);
}
