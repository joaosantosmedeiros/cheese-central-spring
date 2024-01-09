package joao.pedro.productsapi.main;

import joao.pedro.productsapi.application.gateways.CategoryGateway;
import joao.pedro.productsapi.application.usecases.category.CreateCategoryInteractor;
import joao.pedro.productsapi.application.usecases.category.FindCategoryInteractor;
import joao.pedro.productsapi.infrastructure.controllers.category.CategoryDtoMapper;
import joao.pedro.productsapi.infrastructure.gateways.category.CategoryEntityMapper;
import joao.pedro.productsapi.infrastructure.gateways.category.CategoryRepositoryGateway;
import joao.pedro.productsapi.infrastructure.persistence.category.CategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {
    @Bean
    CreateCategoryInteractor createCategoryUseCase(CategoryGateway categoryGateway) {
        return new CreateCategoryInteractor(categoryGateway);
    }

    @Bean
    FindCategoryInteractor findCategoryUseCase(CategoryGateway categoryGateway) {
        return new FindCategoryInteractor(categoryGateway);
    }

    @Bean
    CategoryGateway categoryGateway(CategoryRepository categoryRepository, CategoryEntityMapper categoryEntityMapper) {
        return new CategoryRepositoryGateway(categoryRepository, categoryEntityMapper);
    }

    @Bean
    CategoryEntityMapper categoryEntityMapper() {
        return new CategoryEntityMapper();
    }

    @Bean
    CategoryDtoMapper categoryDtoMapper() {
        return new CategoryDtoMapper();
    }
}
