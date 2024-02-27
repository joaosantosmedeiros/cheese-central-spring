package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.usecase.product.CreateProductUseCase.Input;
import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CreateProductUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private ProductGateway productGateway;

    @Autowired
    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a product correctly")
    void createProductSuccess() {
        Category category = new Category(UUID.randomUUID(), "valid_category_name");
        Product product = new Product(
                UUID.randomUUID(),
                "valid_product_name",
                "valid_description",
                "valid_image_url",
                9.99, category
        );

        when(categoryGateway.findById(category.getId())).thenReturn(Optional.of(category));

        var input = new Input(
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                category.getId()
        );

        var output = createProductUseCase.execute(input);

        assertEquals(output.data().getName(), product.getName());
        assertEquals(output.data().getDescription(), product.getDescription());
        assertEquals(output.data().getImageUrl(), product.getImageUrl());
        assertEquals(output.data().getPrice(), product.getPrice());
        assertEquals(output.data().getCategory().getId(), product.getCategory().getId());
        assertEquals(output.data().getCategory().getName(), product.getCategory().getName());
        assertEquals(output.data().getCategoryId(), product.getCategoryId());
    }

    @Test
    @DisplayName("Should throw if an invalid category is passed")
    void createProductError() {
        Category category = new Category(UUID.randomUUID(), "valid_category_name");

        when(categoryGateway.findById(category.getId())).thenReturn(Optional.empty());

        var input = new Input(
                "any_name",
                "any_description",
                "any_image_url",
                9.99,
                category.getId()
        );

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
           createProductUseCase.execute(input);
        });

        assertEquals("Category not found.", thrown.getMessage());
    }
}