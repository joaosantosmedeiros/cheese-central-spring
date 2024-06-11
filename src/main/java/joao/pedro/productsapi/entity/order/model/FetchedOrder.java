package joao.pedro.productsapi.entity.order.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FetchedOrder {

    private UUID id;
    private UUID accountId;
    private LocalDateTime dateTime;
    private UUID paymentId;

    public FetchedOrder(Order order) {
        this.id = order.getId();
        this.accountId = order.getAccount().getId();
        this.dateTime = order.getDate();
        this.paymentId = order.getPayment().getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchedOrder that = (FetchedOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FetchedOrder{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", dateTime=" + dateTime +
                ", paymentId=" + paymentId +
                '}';
    }
}
