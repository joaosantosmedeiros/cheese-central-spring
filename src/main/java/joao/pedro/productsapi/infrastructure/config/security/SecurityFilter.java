package joao.pedro.productsapi.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import joao.pedro.productsapi.entity.exceptions.EntityNotFoundException;
import joao.pedro.productsapi.entity.exceptions.NotAuthorizedException;
import joao.pedro.productsapi.infrastructure.config.db.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null) {
            var login = tokenService.validateToken(token);
            var account = accountRepository.findByEmail(login).orElseThrow(() -> new EntityNotFoundException("Account"));
            if(account.isDeleted()) throw new NotAuthorizedException();
            UserDetails userDetails = (UserDetails) account;

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails != null ? userDetails.getAuthorities() : null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        return authHeader == null ? null : authHeader.replace("Bearer ", "");
    }
}
