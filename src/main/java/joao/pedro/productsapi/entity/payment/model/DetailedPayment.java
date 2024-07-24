package joao.pedro.productsapi.entity.payment.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record DetailedPayment(UUID id, PaymentStatus paymentStatus, double price, double discount, double finalPrice, LocalDateTime paymentDate) {
    public DetailedPayment(Payment payment) {
        this(
                payment.getId(),
                payment.getPaymentStatus(),
                payment.getPrice(),
                payment.getDiscount(),
                payment.getFinalPrice(),
                payment.getPaymentDate()
        );
    }
}
