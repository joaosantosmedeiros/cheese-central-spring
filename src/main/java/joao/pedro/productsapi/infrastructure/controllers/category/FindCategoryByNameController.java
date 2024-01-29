package joao.pedro.productsapi.infrastructure.controllers.category;

import joao.pedro.productsapi.entity.category.model.Category;
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
public class FindCategoryByNameController {

    private final FindCategoryByNameUseCase findCategoryByNameUseCase;

    @GetMapping("/category/{name}")
    public ResponseEntity<Response> findCategory(@PathVariable("name") String name) {
        var output = findCategoryByNameUseCase.execute(new Input(name));

        HttpStatus httpStatus = output.status() == Boolean.FALSE ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(new Response(
           output.status(),
           output.message(),
           output.data()
        ));
    }

    private record Response(
            Boolean status,
            String message,
            Category data
    ){}
}
