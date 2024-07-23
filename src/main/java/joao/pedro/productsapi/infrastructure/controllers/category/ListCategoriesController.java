package joao.pedro.productsapi.infrastructure.controllers.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.category.ListCategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories.")
public class ListCategoriesController {

    private final ListCategoryUseCase listCategoryUseCase;

    @GetMapping("/category")
    @Operation(description = "Categories listing", summary = "List all categories", responses = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public ResponseEntity<StandardResponse<List<Category>>> listCategories() {
        var output = listCategoryUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
                "Listing all categories.",
                true,
                output.data()
        ));
    }
}
