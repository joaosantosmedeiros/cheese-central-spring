package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.DetailedOrder;
import joao.pedro.productsapi.entity.orderProduct.gateway.OrderProductGateway;

import java.util.UUID;

public class FindOrderByIdUseCase {

    private final OrderGateway orderGateway;
    private final OrderProductGateway orderProductGateway;

    public FindOrderByIdUseCase(OrderGateway orderGateway, OrderProductGateway orderProductGateway) {
        this.orderGateway = orderGateway;
        this.orderProductGateway = orderProductGateway;
    }

    public Output findById(Input input) {
        var order = orderGateway.findById(input.orderId).orElseThrow(() -> new EntityNotFoundException("Order"));
        var orderProducts = orderProductGateway.findByOrder(order);
        return new Output(new DetailedOrder(order, orderProducts));
    }

    public record Input(UUID orderId) {}
    public record Output(DetailedOrder data) {}
}