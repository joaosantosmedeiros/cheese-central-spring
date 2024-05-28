package joao.pedro.productsapi.entity.cart.model;

import java.util.UUID;

public class FetchedCart {
    private UUID id;
    private boolean isActive;
    private UUID accountId;

    public FetchedCart(Cart cart){
        this.id = cart.getId();
        this.isActive = cart.isActive();
        this.accountId = cart.getAccount().getId();
    }

    public FetchedCart(UUID id, boolean isActive, UUID accountId) {
        this.id = id;
        this.isActive = isActive;
        this.accountId = accountId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
