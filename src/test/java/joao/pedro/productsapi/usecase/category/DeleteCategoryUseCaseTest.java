package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
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

import java.util.Optional;
import java.util.UUID;

class DeleteCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

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
        UUID id = UUID.randomUUID();

        when(categoryGateway.findById(id)).thenReturn(Optional.of(new Category("random category")));

        assertDoesNotThrow(() -> {
            deleteCategoryUseCase.execute(new DeleteCategoryUseCase.Input(id));
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
}
