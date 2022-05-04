package br.com.meli.dhprojetointegrador.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Elemento da cadeia de responsabilidade do filtro que é recebe as exceções lançadas durante a validação de credenciais
 * e os direciona para tratamento no ControllerAdvice.
 * @author Maik
 */
@Slf4j
@Component
@AllArgsConstructor
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Repassa a tarefa de filtragem para o próximo elemento da cadeia, caso capture uma exceção, encaminha por meio
     * do handlerExceptionResolver ao ControllerAdvice para tratamento e retorno de payload adequado
     * @author Maik
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("An error occured during the executino of the security filter: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
