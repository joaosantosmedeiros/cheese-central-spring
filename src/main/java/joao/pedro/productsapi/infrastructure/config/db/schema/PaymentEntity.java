package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.payment.model.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PAYMENTS")
public class PaymentEntity {

    @Id
    private UUID id;
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private double discount;
    @Column(nullable = false)
    private double finalPrice;
    @Column(nullable = true)
    private LocalDateTime paymentDate;


}
