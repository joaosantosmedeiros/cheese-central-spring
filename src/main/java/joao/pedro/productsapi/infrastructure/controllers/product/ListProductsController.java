package joao.pedro.productsapi.infrastructure.controllers.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.product.ListProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Products", description = "Operation related to products.")
public class ListProductsController {

    private final ListProductUseCase listProductUseCase;

    @GetMapping("/product")
    @Operation(description = "Products listing", summary = "List all products")
    public ResponseEntity<StandardResponse<List<Product>>> listProducts() {
        var output = listProductUseCase.execute();

        return ResponseEntity.ok(new StandardResponse<>(
                "Listing all products.",
                true,
                output.data()
        ));
    }
}
