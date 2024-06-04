package joao.pedro.productsapi.usecase.cartProduct;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.cartProduct.gateway.CartProductGateway;
import joao.pedro.productsapi.entity.cartProduct.model.CartProduct;
import joao.pedro.productsapi.entity.cartProduct.model.FetchedCartProduct;
import joao.pedro.productsapi.entity.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindCartProductByCartUseCaseTest {

    @Mock
    private CartProductGateway cartProductGateway;

    @InjectMocks
    @Autowired
    private FindCartProductByCartUseCase findCartProductByCartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("It should list cart products correctly")
    public void findCartProductsSuccess() {
        var cartProductList = List.of(
                new CartProduct(UUID.randomUUID(), 2, new Cart(true, null), new Product("name", "desc", "image", 12d, null)),
                new CartProduct(UUID.randomUUID(), 3, new Cart(true, null), new Product("name", "desc", "image", 12d, null)),
                new CartProduct(UUID.randomUUID(), 4, new Cart(true, null), new Product("name", "desc", "image", 12d, null))
        );
        var fetchedProductsList = new FetchedCartProduct[]{
                new FetchedCartProduct(cartProductList.get(0)),
                new FetchedCartProduct(cartProductList.get(1)),
                new FetchedCartProduct(cartProductList.get(2))
        };

        when(cartProductGateway.findByCartId(any())).thenReturn(cartProductList);

        var output = findCartProductByCartUseCase.execute(new FindCartProductByCartUseCase.Input(UUID.randomUUID())).data();

        assertArrayEquals(output, fetchedProductsList);
    }
}