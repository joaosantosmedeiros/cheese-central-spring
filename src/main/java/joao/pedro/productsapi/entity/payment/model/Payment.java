package joao.pedro.productsapi.entity.payment.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private UUID id;
    private PaymentStatus paymentStatus;
    private double price;
    private double discount;
    private double finalPrice;
    private LocalDateTime paymentDate;

    public Payment(UUID id, PaymentStatus paymentStatus, double price, double discount, double finalPrice, LocalDateTime paymentDate) {
        this.id = id;
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.paymentDate = paymentDate;
    }

    public Payment(PaymentStatus paymentStatus, double price, double discount, double finalPrice, LocalDateTime paymentDate) {
        this.id = UUID.randomUUID();
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.paymentDate = paymentDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
