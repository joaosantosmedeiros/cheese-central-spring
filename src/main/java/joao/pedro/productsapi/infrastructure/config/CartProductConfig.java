package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.usecase.cartProduct.CreateCartProductUseCase;
import joao.pedro.productsapi.usecase.cartProduct.DeleteCartProductUseCase;
import joao.pedro.productsapi.usecase.cartProduct.FindCartProductByCartUseCase;
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

    @Bean
    public FindCartProductByCartUseCase findCartProductByCartUseCase(CartProductGateway cartProductGateway){
        return new FindCartProductByCartUseCase(cartProductGateway);
    }

    @Bean
    public DeleteCartProductUseCase deleteCartProductUseCase(CartProductGateway cartProductGateway){
        return new DeleteCartProductUseCase(cartProductGateway);
    }
}
