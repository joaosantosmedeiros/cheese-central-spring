package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;

import java.util.List;

public class ListOrdersByAccountUseCase {

    private final OrderGateway orderGateway;

    public ListOrdersByAccountUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Output execute(Input input) {
        return new Output(orderGateway.findByAccount(input.account).stream().map(FetchedOrder::new).toList());
    }

    public record Input(Account account){};
    public record Output(List<FetchedOrder> data){};
}
