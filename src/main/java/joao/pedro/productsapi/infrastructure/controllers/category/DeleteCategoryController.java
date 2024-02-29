package joao.pedro.productsapi.infrastructure.controllers.category;

import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase;
import joao.pedro.productsapi.usecase.category.DeleteCategoryUseCase.Input;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class DeleteCategoryController {

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") UUID id){
        this.deleteCategoryUseCase.execute(new Input(id));
    }
}
