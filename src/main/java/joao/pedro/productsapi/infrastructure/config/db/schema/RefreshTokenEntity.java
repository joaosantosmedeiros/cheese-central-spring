package joao.pedro.productsapi.infrastructure.config.db.schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TOKENS")
@Getter
@NoArgsConstructor
@Setter
public class RefreshTokenEntity {

    private final long ONE_DAY = 86400L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "account_email", referencedColumnName = "email")
    private AccountEntity account;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expirationDate;

    public RefreshTokenEntity(AccountEntity account) {
        this.expirationDate = Instant.now().plusSeconds(ONE_DAY * 2);
        this.token = UUID.randomUUID().toString();
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
        return id == that.id && Objects.equals(account, that.account) && Objects.equals(token, that.token) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, token, expirationDate);
    }
}
