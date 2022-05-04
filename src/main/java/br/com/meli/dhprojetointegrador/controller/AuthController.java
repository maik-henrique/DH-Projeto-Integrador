package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.UserCredentialsRequest;
import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleType;
import br.com.meli.dhprojetointegrador.service.TokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/security")
@AllArgsConstructor
public class AuthController {
    private final ModelMapper modelMapper;
    private final TokenAuthenticationService tokenAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserCredentialsRequest userCredentialsRequest) {
        User user = modelMapper.map(userCredentialsRequest, User.class);
        JwtToken jwtToken = tokenAuthenticationService.authenticate(user);

        return ResponseEntity.ok(jwtToken);
    }
    
}
