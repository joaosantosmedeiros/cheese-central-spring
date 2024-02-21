package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.when;

class CreateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Autowired
    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a category successfully")
    void createCategorySuccess() {
        String name = "valid name";

        when(categoryGateway.findByName(name)).thenReturn(Optional.empty());

        var output = createCategoryUseCase.execute(new CreateCategoryUseCase.Input(name));

        assertThat(output).isNotNull();
    }

    @Test
    @DisplayName("Should throw if an invalid category name is provided")
    void createCategoryError() {
        String name = "invalid name";

        when(categoryGateway.findByName(name)).thenReturn(Optional.of(new Category(name)));

        Exception thrown = assertThrows(EntityAlreadyExistsException.class, () -> {
            createCategoryUseCase.execute(new CreateCategoryUseCase.Input(name));
        });

        assertEquals("Category already exists.", thrown.getMessage());
    }
}
