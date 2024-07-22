package joao.pedro.productsapi.entity.order.model;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.payment.model.Payment;
import joao.pedro.productsapi.entity.payment.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class DetailedOrder {

    private final UUID id;
    private final LocalDateTime date;
    private final DetailedPayment payment;
    private final DetailedAccount account;

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public DetailedPayment getPayment() {
        return payment;
    }

    public DetailedAccount getAccount() {
        return account;
    }

    public DetailedOrder(Order order) {
        this.id = order.getId();
        this.date = order.getDate();
        this.account = order.getAccount() != null ? new DetailedAccount(order.getAccount()) : null;
        this.payment = order.getPayment() != null ? new DetailedPayment(order.getPayment()) : null;
    }

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

    public record DetailedAccount(UUID id, String username, String email, Role role, boolean isDeleted) {
        public DetailedAccount(Account account) {
            this(account.getId(), account.getUsername(), account.getEmail(), account.getRole(), account.isDeleted());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailedOrder that = (DetailedOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DetailedOrder{" +
                "id=" + id +
                ", date=" + date +
                ", payment=" + payment +
                ", account=" + account +
                '}';
    }
}
