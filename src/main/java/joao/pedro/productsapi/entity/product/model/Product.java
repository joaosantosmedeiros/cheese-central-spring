package joao.pedro.productsapi.entity.product.model;

import joao.pedro.productsapi.entity.category.model.Category;

import java.util.UUID;

public class Product {

    public Product(UUID id, String name, String description, String imageUrl, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.categoryId = category.getId();
        this.category = category;
    }

    public Product(String name, String description, String imageUrl, Double price, Category category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.categoryId = category.getId();
        this.category = category;
    }

    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private UUID categoryId;
    private Category category;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
