package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private PaymentEntity payment;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private AccountEntity account;
}
