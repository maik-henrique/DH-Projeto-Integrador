package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.UserCredentialsRequest;
import br.com.meli.dhprojetointegrador.entity.JwtToken;
import br.com.meli.dhprojetointegrador.entity.Role;
import br.com.meli.dhprojetointegrador.entity.User;
import br.com.meli.dhprojetointegrador.enums.RoleEnum;
import br.com.meli.dhprojetointegrador.enums.TokenTypeEnum;
import br.com.meli.dhprojetointegrador.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTests extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Login - Valida e retorna credenciais do usuário")
    public void login_shouldJwtToken_whenUserCredentialsAreValid() throws Exception {
        final String username = "joooj";
        final String password = "senha";

        setupUser(username, password);

        UserCredentialsRequest request = UserCredentialsRequest.builder()
                .username(username)
                .password(password)
                .build();

        String requestContent = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mvc.perform(post("/security/login")
                .content(requestContent)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        JwtToken jwtToken = objectMapper.readValue(responsePayload, JwtToken.class);

        assertEquals(TokenTypeEnum.BEARER, jwtToken.getTokenTypeEnum());
        assertFalse(jwtToken.getToken().isBlank());
        assertFalse(jwtToken.getExpirationDate().isBlank());
    }

    @Test
    @DisplayName("Login - Retorno de 401 caso as credenciais fornecidas sejam inválidas")
    public void login_shouldReturnUnauthorized_whenUserCredentialsAreInvalid() throws Exception {
        UserCredentialsRequest request = UserCredentialsRequest.builder()
                .username("123456")
                .password("123456")
                .build();

        String requestContent = objectMapper.writeValueAsString(request);

        mvc.perform(post("/security/login")
                .content(requestContent)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    private void setupUser(String username, String password) {
        Role adminRole = Role.builder().role(RoleEnum.ADMIN).build();
        User user = User.builder()
                .role(Set.of(adminRole))
                .username(username)
                .password(password)
                .build();

        tokenAuthenticationService.signUp(user);
    }
}
