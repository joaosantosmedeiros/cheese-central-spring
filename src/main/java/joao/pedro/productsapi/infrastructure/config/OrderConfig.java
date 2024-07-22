package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.order.gateway.OrderGateway;
import joao.pedro.productsapi.entity.orderProduct.gateway.OrderProductGateway;
import joao.pedro.productsapi.entity.payment.gateway.PaymentGateway;
import joao.pedro.productsapi.usecase.order.CreateOrderUseCase;
import joao.pedro.productsapi.usecase.order.ListOrdersByAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderGateway orderGateway, PaymentGateway paymentGateway, OrderProductGateway orderProductGateway){
        return new CreateOrderUseCase(orderGateway, paymentGateway, orderProductGateway);
    }

    @Bean
    public ListOrdersByAccountUseCase listOrdersByUserUseCase(OrderGateway orderGateway){
        return new ListOrdersByAccountUseCase(orderGateway);
    }
}
