package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;
import org.checkerframework.checker.units.qual.C;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateProductUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    @Autowired
    private UpdateProductUseCase updateProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should update a product correctly")
    void updateProductSuccess() {
        Category category = new Category("any_category_name");
        Product product = new Product(
                "any_name",
                "any_desc",
                "any_image",
                9.99,
                category
        );
        when(productGateway.findById(product.getId())).thenReturn(Optional.of(product));
        when(categoryGateway.findById(category.getId())).thenReturn(Optional.of(category));

        var output = updateProductUseCase.execute(new UpdateProductUseCase.Input(
                product.getId(),
                "new_name",
                "new_desc",
                "new_image",
                10.00,
                category.getId()
        ));

        var expected = new Product(
                product.getId(),
                "new_name",
                "new_desc",
                "new_image",
                10.00,
                category
        );

        assertEquals(expected, output.data());
    }

    @Test
    @DisplayName("Should throw  if an invalid product is passed")
    void updateProductInvalidProduct() {
        var product = new Product(
                "any_name",
                "any_desc",
                "any_image",
                9.99,
                new Category("any_c_name")
        );

        when(productGateway.findById(product.getId())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
           updateProductUseCase.execute(new UpdateProductUseCase.Input(
                   product.getId(),
                   "new_name",
                   "new_desc",
                   "new_image",
                   10.0,
                   UUID.randomUUID()
           ));
        });

        assertEquals("Product not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw if an invalid category is passed")
    void updateProductInvalidCategory() {
        UUID id = UUID.randomUUID();
        when(productGateway.findById(any())).thenReturn(Optional.of(new Product("name", "desc", "img", 4.44, new Category("name"))));
        when(categoryGateway.findById(id)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            updateProductUseCase.execute(new UpdateProductUseCase.Input(
                    UUID.randomUUID(),
                    "new_name",
                    "new_desc",
                    "new_image",
                    10.0,
                    id
            ));
        });

        assertEquals("Category not found.", thrown.getMessage());
    }
}