package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.ObjectInUseException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class DeleteCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private ProductGateway productGateway;

    @Autowired
    @InjectMocks
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should delete a category successfully")
    void deleteCategorySuccess() {
        Category category = new Category("any_name");

        when(categoryGateway.findById(category.getId())).thenReturn(Optional.of(category));
        when(productGateway.findByCategory(category)).thenReturn(List.of());
        assertDoesNotThrow(() -> {
            deleteCategoryUseCase.execute(new DeleteCategoryUseCase.Input(category.getId()));
        });

        verify(categoryGateway, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw if no category is found")
    void deleteCategoryError() {
        UUID id = UUID.randomUUID();

        when(categoryGateway.findById(id)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class ,() -> {
            deleteCategoryUseCase.execute(new DeleteCategoryUseCase.Input(id));
        });

        assertEquals("Category not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw if the category is in sue")
    void deleteCategoryInUse() {
        Category category = new Category("any_name");

        when(categoryGateway.findById(category.getId())).thenReturn(Optional.of(category));
        when(productGateway.findByCategory(category)).thenReturn(List.of(new Product("name", "desc", "image", 2d, category)));
        Exception thrown = assertThrows(ObjectInUseException.class ,() -> {
            deleteCategoryUseCase.execute(new DeleteCategoryUseCase.Input(category.getId()));
        });

        assertEquals("Category already in use.", thrown.getMessage());
    }
}
