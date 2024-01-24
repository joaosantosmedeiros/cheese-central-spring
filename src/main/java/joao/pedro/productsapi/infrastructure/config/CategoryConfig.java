package joao.pedro.productsapi.infrastructure.config;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;

import joao.pedro.productsapi.usecase.category.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {
    @Bean
    ListCategoryUseCase listCategoryUseCase(CategoryGateway categoryGateway) {
        return new ListCategoryUseCase(categoryGateway);
    }

    @Bean
    FindCategoryByNameUseCase findCategoryByNameUseCase(CategoryGateway categoryGateway) {
        return new FindCategoryByNameUseCase(categoryGateway);
    }

    @Bean
    CreateCategoryUseCase createCategoryUseCase(CategoryGateway categoryGateway){
        return new CreateCategoryUseCase(categoryGateway);
    }

    @Bean
    UpdateCategoryUseCase updateCategoryUseCase(CategoryGateway categoryGateway){
        return new UpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    DeleteCategoryUseCase deleteCategoryUseCase(CategoryGateway categoryGateway){
        return new DeleteCategoryUseCase(categoryGateway);
    }
}
