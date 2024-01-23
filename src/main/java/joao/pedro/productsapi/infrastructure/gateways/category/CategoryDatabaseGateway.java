package joao.pedro.productsapi.infrastructure.gateways.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.infrastructure.config.db.repository.CategoryRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.CategoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CategoryDatabaseGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> list() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> new Category(
                        categoryEntity.getId(),
                        categoryEntity.getName()
                )).toList();
    }

    @Override
    public Optional<Category> find(String name) {
        return categoryRepository.findByName(name).map(categoryEntity -> new Category(
                categoryEntity.getId(),
                categoryEntity.getName()
        ));
    }

    @Override
        public Category create(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category.getId(), category.getName());
        categoryRepository.save(categoryEntity);
        return new Category(categoryEntity.getId(), categoryEntity.getName());
    }
}
