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

    @PostMapping("/category/create")
    public ResponseEntity<Response> create(@RequestBody @Valid Request request){
        var output = createCategoryUseCase.execute(new Input(request.name()));
        HttpStatus httpStatus = output.status() == Boolean.FALSE ? HttpStatus.BAD_REQUEST : HttpStatus.OK;

        return ResponseEntity.status(httpStatus).body(new Response(output.status(), output.message(), output.data()));
    }

     private record Request(
            @NotBlank
            String name
    ){}

    private record Response(
        Boolean status,
        String message,
        Category data
    ) {}
}