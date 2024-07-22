package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.DetailedOrder;

import java.util.UUID;

public class FindOrderByIdUseCase {

    private final OrderGateway orderGateway;

    public FindOrderByIdUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Output findById(Input input) {
        var order = orderGateway.findById(input.orderId).orElseThrow(() -> new EntityNotFoundException("Order"));

        return new Output(new DetailedOrder(order));
    }

    public record Input(UUID orderId) {}
    public record Output(DetailedOrder data) {}
}