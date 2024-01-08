package joao.pedro.productsapi.infrastructure.gateways.category;

import joao.pedro.productsapi.domain.entity.Category;
import joao.pedro.productsapi.infrastructure.persistence.category.CategoryEntity;

public class CategoryEntityMapper {
    CategoryEntity toEntity(Category categoryDomainObj) {
        return new CategoryEntity(categoryDomainObj.name());
    }

    Category toDomainObj(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getName());
    }
}
