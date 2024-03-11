package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import joao.pedro.productsapi.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ACCOUNTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isDeleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public AccountEntity(String username, String email, String password, boolean isDeleted, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isDeleted = isDeleted;
        this.role = role;
    }
}
