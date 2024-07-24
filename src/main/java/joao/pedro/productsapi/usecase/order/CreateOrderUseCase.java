package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.orderProduct.gateway.OrderProductGateway;
import joao.pedro.productsapi.entity.orderProduct.model.OrderProduct;
import joao.pedro.productsapi.entity.payment.gateway.PaymentGateway;
import joao.pedro.productsapi.entity.payment.model.Payment;
import joao.pedro.productsapi.entity.payment.model.PaymentStatus;

import java.time.LocalDateTime;

public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final PaymentGateway paymentGateway;
    private final OrderProductGateway orderProductGateway;


    public CreateOrderUseCase(OrderGateway orderGateway, PaymentGateway paymentGateway, OrderProductGateway orderProductGateway) {
        this.orderGateway = orderGateway;
        this.paymentGateway = paymentGateway;
        this.orderProductGateway = orderProductGateway;
    }

    public Output execute(Input input) {
        if(input.cart.getCartProducts().isEmpty()){
            throw new BadRequestException("Order must contain at least one item.");
        }

        double value = input.cart.getCartProducts().stream()
                .map(cartProduct -> cartProduct.getProduct().getPrice() * cartProduct.getAmount())
                .reduce(Double::sum)
                .orElse(0d);

        Payment payment = paymentGateway.create(new Payment(
                PaymentStatus.PENDING,
                value,
                0d,
                null,
                null
        ));

        if(payment.getFinalPrice() < 0){
            throw new BadRequestException("Final price must be positive.");
        }

        Order order = new Order(
                input.cart.getAccount(),
                LocalDateTime.now(),
                payment
        );

        var createdOrder = orderGateway.create(order);

        for(CartProduct cp : input.cart().getCartProducts() ){
            orderProductGateway.create(new OrderProduct(
                    order,
                    cp.getProduct(),
                    cp.getAmount(),
                    cp.getProduct().getPrice() * cp.getAmount()
            ));
        }

        payment.setOrder(createdOrder);
        paymentGateway.create(payment);

        return new Output(new FetchedOrder(createdOrder));
    }

    public record Input(Cart cart) {}
    public record Output(FetchedOrder data) {}
}
