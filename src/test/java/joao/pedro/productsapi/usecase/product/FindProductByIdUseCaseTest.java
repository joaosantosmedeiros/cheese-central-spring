package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase.Input;
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

class FindProductByIdUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Autowired
    @InjectMocks
    private FindProductByIdUseCase findProductByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find a product successfully")
    void findProductSuccess() {
        Product product = new Product("name", "desc", "img", 44.4, new Category("name"));
        when(productGateway.findById(product.getId())).thenReturn(Optional.of(product));

        var output = findProductByIdUseCase.execute(new Input(product.getId()));

        assertEquals(product, output.data());
    }

    @Test
    @DisplayName("Should throw if an unexistent product is passed")
    void findProductError() {
        UUID id = UUID.randomUUID();
        when(productGateway.findById(id)).thenReturn(Optional.empty());

        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            findProductByIdUseCase.execute(new Input(id));
        });

        assertEquals("Product not found.", thrown.getMessage());
    }
}