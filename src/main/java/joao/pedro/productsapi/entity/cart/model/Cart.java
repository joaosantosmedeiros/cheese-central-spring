package joao.pedro.productsapi.entity.cart.model;

import joao.pedro.productsapi.entity.account.model.Account;

import java.util.Objects;
import java.util.UUID;

public class Cart {
    private UUID id;
    private boolean isActive;
    private Account account;

    public Cart(UUID id, boolean isActive, Account account) {
        this.id = id;
        this.isActive = isActive;
        this.account = account;
    }


    public Cart(boolean isActive, Account account) {
        this.id = UUID.randomUUID();
        this.isActive = isActive;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return isActive == cart.isActive && Objects.equals(id, cart.id) && Objects.equals(account, cart.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, account);
    }
}
