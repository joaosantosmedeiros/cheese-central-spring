package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import org.assertj.core.api.Assertions;
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

class FindCategoryByNameUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Autowired
    @InjectMocks
    private FindCategoryByNameUseCase findCategoryByNameUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find a category successfully")
    void findCategoryByNameSuccess () {
        String name = "valid name";
        when(categoryGateway.findByName(name)).thenReturn(Optional.of(new Category(name)));

        var output = findCategoryByNameUseCase.execute(new FindCategoryByNameUseCase.Input(name));
        assertThat(output).isNotNull();
    }

    @Test
    @DisplayName("Should throw exception when a category is not found")
    void findCategoryByNameError () {
        String name = "valid name";
        when(categoryGateway.findByName(name)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            findCategoryByNameUseCase.execute(new FindCategoryByNameUseCase.Input(name));
        });

        assertEquals("Category not found.", thrown.getMessage());
    }
}
