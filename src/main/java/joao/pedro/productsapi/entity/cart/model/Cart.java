package joao.pedro.productsapi.entity.cart.model;

import java.util.UUID;

public class Cart {
    private UUID id;
    private boolean isActive;
    private UUID accountId;

    public Cart(UUID id, boolean isActive, UUID accountId) {
        this.id = id;
        this.isActive = isActive;
        this.accountId = accountId;
    }


    public Cart(boolean isActive, UUID accountId) {
        this.id = UUID.randomUUID();
        this.isActive = isActive;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
