package joao.pedro.productsapi.infrastructure.gateways.category;

import joao.pedro.productsapi.application.gateways.CategoryGateway;
import joao.pedro.productsapi.domain.entity.Category;
import joao.pedro.productsapi.domain.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.infrastructure.persistence.category.CategoryEntity;
import joao.pedro.productsapi.infrastructure.persistence.category.CategoryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryRepositoryGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public Category findCategory(String name) {
        CategoryEntity category = categoryRepository.findByName(name);
        return categoryEntityMapper.toDomainObj(category);
    }

    @Override
    public Category createCategory(Category categoryDomainObj) {

         var categoryExists = this.findCategory(categoryDomainObj.name());
         if(categoryExists != null) {
            throw new EntityAlreadyExistsException("Category");
         }

        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(categoryDomainObj);
        CategoryEntity savedObj = categoryRepository.save(categoryEntity);
        return categoryEntityMapper.toDomainObj(savedObj);
    }
}
