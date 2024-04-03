package joao.pedro.productsapi.infrastructure.config.security;

import joao.pedro.productsapi.infrastructure.config.db.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Account not found."));
        if(account.isDeleted()){
            throw new UsernameNotFoundException("Account not found.");
        }

        return account;
    }
}
