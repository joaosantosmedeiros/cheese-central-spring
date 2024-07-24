package joao.pedro.productsapi.entity.order.model;

import joao.pedro.productsapi.entity.account.model.DetailedAccount;
import joao.pedro.productsapi.entity.orderProduct.model.DetailedOrderProduct;
import joao.pedro.productsapi.entity.orderProduct.model.OrderProduct;
import joao.pedro.productsapi.entity.payment.model.DetailedPayment;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public class DetailedOrder {

    private final UUID id;
    private final LocalDateTime date;
    private final DetailedPayment payment;
    private final DetailedAccount account;
    private final List<DetailedOrderProduct> orderProducts;

    public DetailedOrder(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.account = order.getAccount() != null ? new DetailedAccount(order.getAccount()) : null;
        this.payment = order.getPayment() != null ? new DetailedPayment(order.getPayment()) : null;
        this.orderProducts = List.of();
    }

    public DetailedOrder(Order order, List<OrderProduct> orderProducts) {
        this.id = order.getId();
        this.date = order.getDate();
        this.account = order.getAccount() != null ? new DetailedAccount(order.getAccount()) : null;
        this.payment = order.getPayment() != null ? new DetailedPayment(order.getPayment()) : null;
        this.orderProducts = orderProducts.stream().map(DetailedOrderProduct::new).toList();
    }

    @Override
    public String toString() {
        return "DetailedOrder{" +
                "id=" + id +
                ", date=" + date +
                ", payment=" + payment +
                ", account=" + account +
                ", orderProducts=" + orderProducts +
                '}';
    }
}
