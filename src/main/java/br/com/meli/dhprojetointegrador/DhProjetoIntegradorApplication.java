package br.com.meli.dhprojetointegrador;

import br.com.meli.dhprojetointegrador.processamento.Processamento;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;


@EnableRetry
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableCaching
public class DhProjetoIntegradorApplication {

    public static void main(String[] args) {

        SpringApplication.run(DhProjetoIntegradorApplication.class, args);
        Processamento processamento = new Processamento();
        processamento.gerarArquivoSonar();
    }

}
