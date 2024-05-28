package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.usecase.cart.CreateCartUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    @Bean
    public CreateCartUseCase createCartUseCase(CartGateway cartGateway){
        return new CreateCartUseCase(cartGateway);
    }
}
