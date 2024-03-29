package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.usecase.category.UpdateCategoryUseCase;
import joao.pedro.productsapi.usecase.category.UpdateCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UpdateCategoryController {

    private final UpdateCategoryUseCase updateCategoryUseCase;

    @PutMapping("/category/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") UUID id, @RequestBody @Valid Request request){
        var output = this.updateCategoryUseCase.execute(new Input(id, request.name()));

        return ResponseEntity.status(HttpStatus.OK).body(new Response(
                true,
                "Category updated sucessfully.",
                output.data()
        ));
    }

    private record Request(
            @NotBlank
            String name
    ){}

    private record Response(
            Boolean status,
            String message,
            Category data
    ){}
}
