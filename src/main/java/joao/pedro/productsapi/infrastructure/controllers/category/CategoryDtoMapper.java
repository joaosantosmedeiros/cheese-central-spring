package joao.pedro.productsapi.infrastructure.controllers.category;

import joao.pedro.productsapi.domain.entity.Category;

public class CategoryDtoMapper {
    CreateCategoryResponse toResponse(Category category) {
        return new CreateCategoryResponse(category.name());
    }

    Category toCategory(CreateCategoryRequest categoryRequest) {
        return new Category(categoryRequest.name());
    }
}
