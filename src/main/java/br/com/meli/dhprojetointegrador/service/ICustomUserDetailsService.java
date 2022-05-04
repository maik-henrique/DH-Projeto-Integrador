package br.com.meli.dhprojetointegrador.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ICustomUserDetailsService<T> extends UserDetailsService {
    T createUser(UserDetails user);
}
