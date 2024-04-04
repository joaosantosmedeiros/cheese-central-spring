package joao.pedro.productsapi.infrastructure.gateways.token;

import joao.pedro.productsapi.entity.account.model.Account;
import joao.pedro.productsapi.infrastructure.config.db.repository.RefreshTokenRepository;
import joao.pedro.productsapi.infrastructure.config.db.schema.AccountEntity;
import joao.pedro.productsapi.infrastructure.config.db.schema.RefreshTokenEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RefreshTokenGateway {

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshTokenEntity> findById(long id){
        return refreshTokenRepository.findById(id);
    };

    public RefreshTokenEntity create(Account account) {
        RefreshTokenEntity token = new RefreshTokenEntity(new AccountEntity(account));
        return refreshTokenRepository.save(token);
    }

    public boolean verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return false;
        }

        return true;
    }
}
