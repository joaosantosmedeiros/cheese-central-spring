package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.order.model.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    public Order toOrder() {
        return new Order(
                this.id,
                this.account.toAccount(),
                this.getDate(),
                this.payment.toPayment()
        );
    }
}
