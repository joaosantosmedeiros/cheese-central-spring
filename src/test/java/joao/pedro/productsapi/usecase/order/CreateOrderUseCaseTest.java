package joao.pedro.productsapi.usecase.order;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.enums.Role;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.order.model.Order;
import joao.pedro.productsapi.entity.orderProduct.gateway.OrderProductGateway;
import joao.pedro.productsapi.entity.payment.gateway.PaymentGateway;
import joao.pedro.productsapi.entity.payment.model.Payment;
import joao.pedro.productsapi.entity.payment.model.PaymentStatus;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateOrderUseCaseTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private OrderProductGateway orderProductGateway;

    @Autowired
    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should create an order correctly")
    public void createOrderSuccess() {
        CreateOrderUseCase.Input input = new CreateOrderUseCase.Input(
                new Cart(
                        true,
                        new Account("user", "mail", "pass", false, Role.USER, null),
                        List.of(
                                new CartProduct(3, null, new Product("n", "d", "i", 4d, null)),
                                new CartProduct(2, null, new Product("n", "d", "i", 5d, null)),
                                new CartProduct(1, null, new Product("n", "d", "i", 6d, null))
                        )
                )
        );

        Payment payment = new Payment(
                UUID.randomUUID(),
                PaymentStatus.PENDING,
                12d,
                0d,
                null
        );
        Order order = new Order(input.cart().getAccount(), null, payment);

        when(paymentGateway.create(any())).thenReturn(payment);
        when(orderGateway.create(any())).thenReturn(order);

        var output = createOrderUseCase.execute(input);

        assertEquals(order.getId(), output.data().getId());
        assertEquals(order.getDate(), output.data().getDateTime());
        assertEquals(input.cart().getAccount().getId(), output.data().getAccountId());
        assertEquals(payment.getId(), output.data().getPaymentId());
    }

    @Test
    @DisplayName("It should throw if no cart items are found")
    public void createOrderNoCartItems() {
        CreateOrderUseCase.Input input = new CreateOrderUseCase.Input(
                new Cart(
                        true,
                        new Account("user", "mail", "pass", false, Role.USER, null),
                        List.of()
                )
        );

        var thrown = assertThrows(BadRequestException.class, () -> {
           createOrderUseCase.execute(input);
        });

        assertEquals("Order must contain at least one item.", thrown.getMessage());
    }

    @Test
    @DisplayName("It should throw if payment is negative")
    public void createOrderNegativePayment() {
        CreateOrderUseCase.Input input = new CreateOrderUseCase.Input(
                new Cart(
                        true,
                        new Account("user", "mail", "pass", false, Role.USER, null),
                        List.of(
                                new CartProduct(3, null, new Product("n", "d", "i", -4d, null))
                        )
                )
        );


        Payment payment = new Payment(PaymentStatus.PENDING,-12d,0d,null);

        when(paymentGateway.create(any())).thenReturn(payment);

        var thrown = assertThrows(BadRequestException.class, () -> {
            createOrderUseCase.execute(input);
        });

        assertEquals("Final price must be positive.", thrown.getMessage());
    }
}