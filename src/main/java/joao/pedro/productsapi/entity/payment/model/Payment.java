package joao.pedro.productsapi.entity.payment.model;

import joao.pedro.productsapi.infrastructure.config.db.schema.PaymentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@ToString
public class Payment {

    private UUID id;
    private PaymentStatus paymentStatus;
    private double price;
    private double discount;
    private double finalPrice;
    private LocalDateTime paymentDate;

    public Payment(PaymentStatus paymentStatus, double price, double discount, LocalDateTime paymentDate ) {
        this.id = UUID.randomUUID();
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.discount = discount;
        this.finalPrice = price - (discount * price);
        this.paymentDate = paymentDate;
    }

    public Payment(UUID id, PaymentStatus paymentStatus, double price, double discount, LocalDateTime paymentDate) {
        this.id = id;
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.discount = discount;
        this.finalPrice = price - (discount * price);
        this.paymentDate = paymentDate;
    }

    public PaymentEntity toPaymentEntity() {
        return new PaymentEntity(
                this.id,
                this.paymentStatus,
                this.price,
                this.discount,
                this.finalPrice,
                this.getPaymentDate()
        );
    }

}
