package joao.pedro.productsapi.usecase.product;

import joao.pedro.productsapi.entity.product.gateway.ProductGateway;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ListProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Autowired
    @InjectMocks
    private ListProductUseCase listProductUseCase;

    @BeforeEach()
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return the products list successfully")
    void listProductsSuccess() {
        when(productGateway.list()).thenReturn(new ArrayList<Product>());

        var output = listProductUseCase.execute();

        verify(productGateway, times(1)).list();
        assertThat(output.data()).isNotNull();
    }
}