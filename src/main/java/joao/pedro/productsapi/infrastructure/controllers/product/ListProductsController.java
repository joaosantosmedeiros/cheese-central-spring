package joao.pedro.productsapi.infrastructure.controllers.product;

import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.usecase.product.ListProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ListProductsController {

    private final ListProductUseCase listProductUseCase;

    @GetMapping("/product")
    public Response listProducts() {
        var output = listProductUseCase.execute();

        return new Response(
                true,
                "Listing all products.",
                output.data()
        );
    }

    public record Response(
            boolean status,
            String message,
            List<Product> data
    ) {}
}
