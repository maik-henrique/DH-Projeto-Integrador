package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.UserCredentialsRequest;
import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.service.TokenAuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

/**
 * Controller utilizado para as requisições de autenticação da aplicação, provendo endpoint para o login
 * @author Maik
 */
@RestController
@RequestMapping("/security")
@AllArgsConstructor
public class AuthController {
    private final ModelMapper modelMapper;
    private final TokenAuthenticationService tokenAuthenticationService;

    /**
     * Endpoint usado para login na aplicação, faz a validaçao em relação as credenciais e retorna o token
     * para ser utilizado nos endpoints protegidos da aplicação
     *
     * @param userCredentialsRequest payload de requisição contendo as credenciais do cliente
     * @author Maik
     * @return token para uso nas requisições seguintes ou 401 caso não suceda em validar o usuário
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserCredentialsRequest userCredentialsRequest) {
        User user = modelMapper.map(userCredentialsRequest, User.class);
        JwtToken jwtToken = tokenAuthenticationService.authenticate(user);

        return ResponseEntity.ok(jwtToken);
    }

}
