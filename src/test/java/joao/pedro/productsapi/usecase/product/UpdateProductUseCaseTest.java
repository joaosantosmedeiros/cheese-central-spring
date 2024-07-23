package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.BadRequestException;
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

import javax.swing.text.html.Option;
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
        var expected = new Product(
                product.getId(),
                "new_name",
                "new_desc",
                "new_image",
                10.00,
                category
        );
        when(categoryGateway.findById(category.getId())).thenReturn(Optional.of(category));
        when(productGateway.findById(product.getId())).thenReturn(Optional.of(product));
        when(productGateway.create(expected)).thenReturn(expected);

        var output = updateProductUseCase.execute(new UpdateProductUseCase.Input(new Product(
                    product.getId(),
                    "new_name",
                    "new_desc",
                    "new_image",
                    10.00,
                    new Category(category.getId(), null)
        )));

        assertEquals(expected, output.data());
    }

    @Test
    @DisplayName("Should throw  if no product is passed")
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
           updateProductUseCase.execute(new UpdateProductUseCase.Input(new Product(
                           product.getId(),
                           "new_name",
                           "new_desc",
                           "new_image",
                           10.0,
                           new Category(UUID.randomUUID(), null)
                   )
           ));
        });

        assertEquals("Product not found.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw if an invalid price is passed")
    void updateProductInvalidData() {
        UUID id = UUID.randomUUID();
        Product product = new Product("name", "desc", "img", 4.44, new Category(id,"name"));
        when(productGateway.findById(any())).thenReturn(Optional.of(product));
        when(categoryGateway.findById(id)).thenReturn(Optional.of(product.getCategory()));

        Exception thrown = assertThrows(BadRequestException.class, () -> {
            updateProductUseCase.execute(new UpdateProductUseCase.Input(
                    new Product(
                            UUID.randomUUID(),
                            "new_name",
                            "new_desc",
                            "new_image",
                            -10.0,
                            new Category(id, null)
                    )
            ));
        });

        assertEquals("Price must be positive.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw if an invalid category is passed")
    void updateProductInvalidCategory() {
        UUID id = UUID.randomUUID();
        when(productGateway.findById(any())).thenReturn(Optional.of(new Product("name", "desc", "img", 4.44, new Category("name"))));
        when(categoryGateway.findById(id)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            updateProductUseCase.execute(new UpdateProductUseCase.Input(
                    new Product(
                            UUID.randomUUID(),
                            "new_name",
                            "new_desc",
                            "new_image",
                            10.0,
                            new Category(id, null)
                    )
            ));
        });

        assertEquals("Category not found.", thrown.getMessage());
    }
}