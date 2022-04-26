package br.com.meli.dhprojetointegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DhProjetoIntegradorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhProjetoIntegradorApplication.class, args);
    }

}
