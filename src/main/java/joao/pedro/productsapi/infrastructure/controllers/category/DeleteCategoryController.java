package joao.pedro.productsapi.infrastructure.controllers.category;

import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase;
import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class DeleteCategoryController {

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("id") UUID id){

        var output = this.deleteCategoryUseCase.execute(new Input(id));

        return ResponseEntity.status(HttpStatus.OK).body(new Response(
                output.status(),
                output.message()
        ));
    }

    private record Response(
            Boolean status,
            String message
    ){}
}
