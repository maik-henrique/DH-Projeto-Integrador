package br.com.meli.dhprojetointegrador.config;

import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Implementação das configurações de segurança da aplicação, só é ativado quando caso o valor configurado em
 * 'security.jwt.enabled' for 'true' caso contrário não é utilizado na cadeia de filtros, resultando em um 'bypass'
 * em relação às requisições.
 * @author Maik
 */
@EnableWebSecurity
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(name = "security.jwt.enabled",
        havingValue = "true"
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private static final String[] BUYER_ENDPOINTS = {".*/fresh-products",".*/fresh-products/list.*", ".*/fresh-products/orders.*"};
    private static final String[] AGENT_ENDPOINTS = {".*/inboundorder.*", ".*/fresh-products/due-date.*", ".*/fresh-products/list.*",
            ".*/fresh-products/warehouse.*"};

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .regexMatchers(".*/admin.*").hasRole(RoleEnum.ADMIN.name())
                .regexMatchers(AGENT_ENDPOINTS).hasAnyRole(RoleEnum.AGENT.name(), RoleEnum.ADMIN.name())
                .regexMatchers(BUYER_ENDPOINTS).hasRole(RoleEnum.BUYER.name())
                .antMatchers("/signup/*", "/login/*").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
