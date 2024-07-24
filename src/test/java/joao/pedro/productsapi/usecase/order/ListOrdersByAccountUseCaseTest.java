package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.FetchedOrder;
import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.payment.model.Payment;
import joao.pedro.productsapi.entity.payment.model.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ListOrdersByAccountUseCaseTest {

    @Mock
    private OrderGateway orderGateway;

    @Autowired
    @InjectMocks
    private ListOrdersByAccountUseCase listOrdersByAccountUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should list an account orders correctly")
    public void listOrdersSuccess() {
        var account = new Account("name","mail@mail.com","password", false, Role.USER,null);
        var order = new Order(account, LocalDateTime.now(),new Payment(PaymentStatus.PENDING, 0d, 0d, LocalDateTime.now()));
        when(orderGateway.findByAccount(account)).thenReturn(List.of(order));

        var orders = listOrdersByAccountUseCase.execute(new ListOrdersByAccountUseCase.Input(account)).data();

        assertEquals(orders.get(0), new FetchedOrder(order));
    }
}