package joao.pedro.productsapi.entity.payment.model;

import joao.pedro.productsapi.entity.order.model.Order;
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
    private Order order;

    public Payment(PaymentStatus paymentStatus, double price, double discount, double finalPrice, LocalDateTime paymentDate, Order order) {
        this.id = UUID.randomUUID();
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.paymentDate = paymentDate;
        this.order = order;
    }

}
