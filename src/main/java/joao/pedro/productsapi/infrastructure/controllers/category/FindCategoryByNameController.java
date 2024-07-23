package joao.pedro.productsapi.infrastructure.controllers.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.infrastructure.dtos.StandardResponse;
import joao.pedro.productsapi.usecase.category.FindCategoryByNameUseCase;
import joao.pedro.productsapi.usecase.category.FindCategoryByNameUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Categories", description = "Operations related to categories.")
public class FindCategoryByNameController {

    private final FindCategoryByNameUseCase findCategoryByNameUseCase;

    @GetMapping("/category/{name}")
    @Operation(description = "Category detail", summary = "Detail a category", responses = {
            @ApiResponse(responseCode="200", description = "Success."),
            @ApiResponse(responseCode="404", description = "Category not found"),
    })
    public ResponseEntity<StandardResponse<Category>> findCategory(@PathVariable("name") String name) {
        var output = findCategoryByNameUseCase.execute(new Input(name));

        return ResponseEntity.status(HttpStatus.OK).body(new StandardResponse<>(
                "Showing found category.",
                true,
                output.data()
        ));
    }
}
