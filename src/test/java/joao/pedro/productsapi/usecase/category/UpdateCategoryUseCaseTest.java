package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.usecase.category.UpdateCategoryUseCase.Input;
import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class UpdateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Autowired
    @InjectMocks
    private UpdateCategoryUseCase updateCategoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should update a category successfully")
    void updateCategorySuccess () {
        Category category = new Category(UUID.randomUUID(), "any_name");
        Input input = new Input(category);

        when(categoryGateway.findById(input.category().getId())).thenReturn(Optional.of(category));
        when(categoryGateway.findByName(input.category().getName())).thenReturn(Optional.empty());
        when(categoryGateway.update(any())).thenReturn(category);

        var result = updateCategoryUseCase.execute(input);

        assertEquals(category, result.data());
    }

    @Test
    @DisplayName("Should throw if an invalid category is passed")
    void updateCategoryNotFoundError () {
        Category category = new Category(UUID.randomUUID(), "any_name");
        Input input = new Input(category);

        when(categoryGateway.findById(input.category().getId())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            updateCategoryUseCase.execute(input);
        });

        assertEquals("Category not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw if the category name is in use")
    void updateCategoryCategoryInUseError () {
        Category category = new Category(UUID.randomUUID(), "any_name");
        Input input = new Input(category);

        when(categoryGateway.findById(input.category().getId())).thenReturn(Optional.of(category));
        when(categoryGateway.findByName(input.category().getName())).thenReturn(Optional.of(new Category(input.category().getName())));

        Exception thrown = assertThrows(EntityAlreadyExistsException.class, () -> {
            updateCategoryUseCase.execute(input);
        });

        assertEquals("Category already exists.", thrown.getMessage());
    }
}