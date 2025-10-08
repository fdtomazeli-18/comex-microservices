package br.com.alura.produto.filter;

import br.com.alura.produto.auth.client.AuthClient;
import br.com.alura.produto.auth.client.TokenValidationResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthClient authClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = request.getHeader("Authorization");
        
        if (token == null || token.isEmpty()) {
            System.out.println("[AuthFilter] Token ausente");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            System.out.println("[AuthFilter] Validando token: " + token.substring(0, Math.min(20, token.length())) + "...");
            TokenValidationResponse validation = authClient.validateToken(token);
            System.out.println("[AuthFilter] Resposta validação: " + validation.isValid());
            if (validation.isValid() == null || !validation.isValid()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (Exception e) {
            System.out.println("[AuthFilter] Erro ao validar: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
