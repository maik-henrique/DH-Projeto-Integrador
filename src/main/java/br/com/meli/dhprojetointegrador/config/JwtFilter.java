package br.com.meli.dhprojetointegrador.config;

import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.exception.AuthException;
import br.com.meli.dhprojetointegrador.service.ICustomUserDetailsService;
import br.com.meli.dhprojetointegrador.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementação de filtro de requisições JWT, só é habilitado para quando o valor de security.jwt.enabled é
 * configurado como 'true' nas configurações da aplicação
 * @author Maik
 */
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(name = "security.jwt.enabled",
        havingValue = "true"

)
public class JwtFilter extends OncePerRequestFilter {

    private final ICustomUserDetailsService<User> jwtUserDetailsManager;
    private final ITokenService tokenService;
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String JWT_HEADER_PREFIX = "Bearer";

    /**
     * Obtém do header o token JWT e então valida com base no token se as credenciais são válidas, caso não sejam
     * lança uma exceção.
     *
     * @author Maik
     * @throws UsernameNotFoundException caso o um usário com as credenciais fornecidas não seja encontrado
     * @throws AuthException caso um problema ocorra durante a validação das credenciais
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, UsernameNotFoundException, AuthException {

        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null && authorizationHeader.startsWith(JWT_HEADER_PREFIX)) {
            String token = authorizationHeader.split(" ")[1];
            String username = tokenService.getUsernameFromToken(token);
            UserDetails userDetails = jwtUserDetailsManager.loadUserByUsername(username);

            if (tokenService.isTokenValid(token, userDetails)) {
                setAuthenticatedUserOnSecurityContext(request, userDetails);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticatedUserOnSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        WebAuthenticationDetails webAuthDetails = webAuthenticationDetailsSource.buildDetails(request);

        usernamePasswordAuthenticationToken.setDetails(webAuthDetails);

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

}
