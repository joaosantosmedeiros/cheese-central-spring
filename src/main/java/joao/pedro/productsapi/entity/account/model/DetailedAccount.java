package joao.pedro.productsapi.entity.account.model;

import joao.pedro.productsapi.entity.enums.Role;

import java.util.UUID;

public record DetailedAccount(UUID id, String username, String email, Role role, boolean isDeleted) {
    public DetailedAccount(Account account) {
        this(account.getId(), account.getUsername(), account.getEmail(), account.getRole(), account.isDeleted());
    }
}