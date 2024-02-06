package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.usecase.product.CreateProductUseCase;
import joao.pedro.productsapi.usecase.product.ListProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    ListProductUseCase listProductUseCase(ProductGateway productGateway, CategoryGateway categoryGateway){
        return new ListProductUseCase(productGateway);
    }

    @Bean
    CreateProductUseCase createProductUseCase( ProductGateway productGateway, CategoryGateway categoryGateway){
        return new CreateProductUseCase(productGateway, categoryGateway);
    }
}
