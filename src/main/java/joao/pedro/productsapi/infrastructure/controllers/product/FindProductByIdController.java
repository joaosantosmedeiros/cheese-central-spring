package joao.pedro.productsapi.infrastructure.controllers.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.product.model.Product;
import joao.pedro.productsapi.infrastructure.config.springdoc.schemas.DefaultErrorResponseSchema;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase;
import joao.pedro.productsapi.usecase.product.FindProductByIdUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Products", description = "Operation related to products.")
public class FindProductByIdController {

    private final FindProductByIdUseCase findProductByIdUseCase;

    @GetMapping("/product/{id}")
    @Operation(description = "Product detailing", summary = "Detail a product", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = DefaultErrorResponseSchema.class))),
    })
    public ResponseEntity<StandardResponse<Product>> findProduct (@PathVariable("id") UUID id) {
        var output = findProductByIdUseCase.execute(new Input(id));

        return ResponseEntity.ok(new StandardResponse<>(
                "Product found successfully.",
                true,
                output.data()
        ));
    }
}
