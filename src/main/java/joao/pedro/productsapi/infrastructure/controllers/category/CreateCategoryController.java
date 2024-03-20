package joao.pedro.productsapi.infrastructure.controllers.category;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.usecase.category.CreateCategoryUseCase;
import joao.pedro.productsapi.usecase.category.CreateCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateCategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    @PostMapping("/category")
    public ResponseEntity<Response> create(@RequestBody @Valid Request request){
        var output = createCategoryUseCase.execute(new Input(request.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(
                true,
                "Category created sucessfully.",
                output.data())
        );
    }

    public record Request(
            @NotBlank
            String name
    ){}

    public record Response(
        Boolean status,
        String message,
        Category data
    ) {}
}
