package br.com.alura.comex.repository;

import br.com.alura.comex.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByAtivo(boolean ativo);

    Page<Cliente> findByAtivo(boolean ativo, Pageable pageable);

    Optional<Cliente> findByIdAndAtivo(Long id, boolean ativo);

    boolean existsByCpf(String cpf);
    
    boolean existsByUsuarioId(Long usuarioId);
}