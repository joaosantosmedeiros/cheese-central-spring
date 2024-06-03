package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.cart.gateway.CartGateway;
import joao.pedro.productsapi.usecase.cart.CreateCartUseCase;
import joao.pedro.productsapi.usecase.cart.DeleteCartUseCase;
import joao.pedro.productsapi.usecase.cart.FindActiveCartByAccountIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    @Bean
    public CreateCartUseCase createCartUseCase(CartGateway cartGateway){
        return new CreateCartUseCase(cartGateway);
    }

    @Bean
    public FindActiveCartByAccountIdUseCase findActiveCartByAccountIdUseCase(CartGateway cartGateway){
        return new FindActiveCartByAccountIdUseCase(cartGateway);
    }

    @Bean
    public DeleteCartUseCase deleteCartUseCase(CartGateway cartGateway){
        return new DeleteCartUseCase(cartGateway);
    }
}
