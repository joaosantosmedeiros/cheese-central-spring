package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Autowired
    @InjectMocks
    private ListCategoryUseCase listCategoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return successfully")
    void listCategoryUseCaseSuccess () {
        when(categoryGateway.list()).thenReturn(new ArrayList<Category>());

        var result = listCategoryUseCase.execute();

        assertThat(result).isNotNull();
    }
}