package br.com.alura.produto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProdutoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProdutoApiApplication.class, args);
    }
}
