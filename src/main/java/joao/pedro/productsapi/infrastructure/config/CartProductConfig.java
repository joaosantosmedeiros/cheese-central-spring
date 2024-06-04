package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.usecase.cartProduct.CreateCartProductUseCase;
import joao.pedro.productsapi.usecase.cartProduct.UpdateCartProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartProductConfig {

    @Bean
    public CreateCartProductUseCase createCartProductUseCase(CartProductGateway cartProductGateway){
        return new CreateCartProductUseCase(cartProductGateway);
    }

    @Bean
    public UpdateCartProductUseCase updateCartProductUseCase(CartProductGateway cartProductGateway){
        return new UpdateCartProductUseCase(cartProductGateway);
    }
}
