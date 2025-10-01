package br.com.alura.comex.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres")
    private String cpf;
    
    @NotBlank(message = "Nome não pode ser nulo ou vazio")
    @Size(min = 2, message = "Nome deve ter pelo menos 2 caracteres")
    private String nome;
    
    @Email(message = "Email deve ter formato válido")
    private String email;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
    
    @Size(max = 100, message = "Logradouro deve ter no máximo 100 caracteres")
    private String logradouro;
    
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String bairro;
    
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;
    
    @Column(name = "uf", length = 2)
    @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
    private String estado;
    
    @Column(length = 9)
    @Size(max = 9, message = "CEP deve ter no máximo 9 caracteres")
    private String cep;
    
    private boolean ativo = true;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}