package joao.pedro.productsapi.entity.order.model;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.payment.model.Payment;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private UUID id;
    private Account account;
    private LocalDateTime date;
    private Payment payment;

    public Order(Account account, LocalDateTime date, Payment payment) {
        this.id = UUID.randomUUID();
        this.account = account;
        this.date = date;
        this.payment = payment;
    }

    public Order(UUID id, Account account, LocalDateTime date, Payment payment) {
        this.id = id;
        this.account = account;
        this.date = date;
        this.payment = payment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", account=" + account +
                ", date=" + date +
                ", payment=" + payment +
                '}';
    }
}
