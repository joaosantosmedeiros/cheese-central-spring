package joao.pedro.productsapi.usecase.category;

import joao.pedro.productsapi.entity.category.gateway.CategoryGateway;
import joao.pedro.productsapi.entity.category.model.Category;
import joao.pedro.productsapi.entity.exceptions.EntityAlreadyExistsException;

public class CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public  CreateCategoryUseCase(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public Output execute(Input input){
        var categoryExists = categoryGateway.findByName(input.name());
        if(categoryExists.isPresent()){
            throw new EntityAlreadyExistsException("Category");
        }

        var data = this.categoryGateway.create(new Category(input.name()));
        return new Output(data);
    }

    public record Input(String name){}
    public record Output(Category data){}
}
