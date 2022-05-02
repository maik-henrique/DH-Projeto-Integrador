package br.com.meli.dhprojetointegrador.config;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Maik
 *
 * Configuração par mecanismo de retry para o datasource, a ideia é que quando a requisição de conexão falhar,
 * haja a retentativa por no máximo 5 vezes, com intervalos espaçados e crescentes num multiplicador de 1.5x
 *
 */
@AllArgsConstructor
public class RetryDataSource extends AbstractDataSource {
    private final DataSource dataSource;

    @Override
    @Retryable(maxAttempts = 5, backoff = @Backoff(maxDelay = 5000, multiplier = 1.5))
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSource.getConnection(username, password);
    }
}
