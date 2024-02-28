package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
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
import static org.mockito.Mockito.*;

class DeleteProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @InjectMocks
    @Autowired
    private DeleteProductUseCase deleteProductUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should delete a product successfully")
    void deleteProductSuccess() {
        Product product = new Product(
                UUID.randomUUID(),
                "any_name",
                "any_desc",
                "any_image",
                9.99,
                new Category(UUID.randomUUID(), "any_category_name")
        );

        when(productGateway.findById(product.getId())).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> {
            deleteProductUseCase.execute(new DeleteProductUseCase.Input(product.getId()));
        });

        verify(productGateway, times(1)).delete(product);
    }

    @Test
    @DisplayName("Should throw if an unexistent product is passed")
    void deleteProductError() {
        UUID id = UUID.randomUUID();

        when(productGateway.findById(id)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
           deleteProductUseCase.execute(new DeleteProductUseCase.Input(id));
        });

        assertEquals("Product not found.", thrown.getMessage());
    }
}