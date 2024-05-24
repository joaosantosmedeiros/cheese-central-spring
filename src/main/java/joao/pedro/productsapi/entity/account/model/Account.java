package joao.pedro.productsapi.entity.account.model;

import joao.pedro.productsapi.entity.cart.model.Cart;
import joao.pedro.productsapi.entity.enums.Role;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Account {

    private final UUID id;
    private String username;
    private String email;
    private String password;
    private final Role role;
    private boolean isDeleted;
    private List<Cart> carts;

    public Account(String username, String email, String password, boolean isDeleted, Role role, List<Cart> carts) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDeleted = isDeleted;
        this.role = role;
        this.carts = carts;
    }

    public Account(UUID id, String username, String email, String password, boolean isDeleted, Role role, List<Cart> carts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDeleted = isDeleted;
        this.role = role;
        this.carts = carts;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return this.role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isDeleted == account.isDeleted && Objects.equals(id, account.id) && Objects.equals(username, account.username) && Objects.equals(email, account.email) && Objects.equals(password, account.password) && role == account.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, role, isDeleted);
    }
}
