package joao.pedro.productsapi.entity.account.model;
import joao.pedro.productsapi.entity.enums.Role;

import java.util.Objects;
import java.util.UUID;

public class FetchedAccount {
    private final UUID id;
    private final String username;
    private final String email;
    private final Role role;
    private final boolean isDeleted;

    public FetchedAccount(Account account){
        this.id = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.role = account.getRole();
        this.isDeleted = account.isDeleted();
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FetchedAccount that = (FetchedAccount) o;
        return isDeleted == that.isDeleted && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, role, isDeleted);
    }
}
