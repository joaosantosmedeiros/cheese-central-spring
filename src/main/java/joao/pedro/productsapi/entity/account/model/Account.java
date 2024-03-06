package joao.pedro.productsapi.entity.account.model;

import java.util.Objects;
import java.util.UUID;

public class Account {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private boolean isDeleted;

    public Account(String username, String email, String password, boolean isAdmin, boolean isDeleted) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
    }

    public Account(UUID id, String username, String email, String password, boolean isAdmin, boolean isDeleted) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
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

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return isAdmin == account.isAdmin && isDeleted == account.isDeleted && Objects.equals(id, account.id) && Objects.equals(username, account.username) && Objects.equals(email, account.email) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, isAdmin, isDeleted);
    }
}
