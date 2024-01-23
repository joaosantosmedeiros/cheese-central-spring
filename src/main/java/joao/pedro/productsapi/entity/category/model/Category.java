package joao.pedro.productsapi.entity.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Category {
    UUID id;
    String name;

    public Category(String name){
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
