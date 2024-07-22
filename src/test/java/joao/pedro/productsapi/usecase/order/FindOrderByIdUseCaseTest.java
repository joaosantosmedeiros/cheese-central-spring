package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.DetailedOrder;
import joao.pedro.productsapi.entity.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindOrderByIdUseCaseTest {

    @Mock
    private OrderGateway orderGateway;

    @Autowired
    @InjectMocks
    private FindOrderByIdUseCase findOrderByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should find an order successfully")
    public void findOrderSuccess() {
        Order order = new Order(
                null,
                LocalDateTime.now(),
                null
        );
        DetailedOrder detailedOrder = new DetailedOrder(order);
        when(orderGateway.findById(order.getId())).thenReturn(Optional.of(order));

        var output = findOrderByIdUseCase.findById(new FindOrderByIdUseCase.Input(order.getId()));

        assertEquals(detailedOrder, output.data());
    }

    @Test
    @DisplayName("It should throw if no order is found")
    public void findOrderNotFound() {
        Order order = new Order(
                null,
                LocalDateTime.now(),
                null
        );
        when(orderGateway.findById(order.getId())).thenReturn(Optional.empty());

        var thrown = assertThrows(EntityNotFoundException.class, () -> {
           findOrderByIdUseCase.findById(new FindOrderByIdUseCase.Input(order.getId()));
        });

        assertEquals("Order not found.", thrown.getMessage());
    }
}