package br.com.alura.produto.auth.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AuthClient {

    private final RestClient client;

    public AuthClient(@LoadBalanced RestClient.Builder builder,
                      @Value("${auth.base-url}") String baseUrl) {
        this.client = builder.baseUrl(baseUrl).build();
    }

    public TokenValidationResponse validateToken(String bearerToken) {
        return client.post()
                .uri("/login/token/validation")
                .header(HttpHeaders.AUTHORIZATION, bearerToken)
                .retrieve()
                .body(TokenValidationResponse.class);
    }

}
