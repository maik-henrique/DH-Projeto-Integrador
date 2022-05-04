package br.com.meli.dhprojetointegrador.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Define extensão do contrado do 'UserDetailsService'
 * @author Maik
 */
public interface ICustomUserDetailsService<T> extends UserDetailsService {
    T createUser(UserDetails user);
}
