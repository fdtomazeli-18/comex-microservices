package br.com.alura.comex.repository;

import br.com.alura.comex.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    List<Categoria> findByAtivo(boolean ativo);
    
    Page<Categoria> findByAtivo(boolean ativo, Pageable pageable);
    
    Optional<Categoria> findByIdAndAtivo(Long id, boolean ativo);
    
    boolean existsByNomeAndAtivo(String nome, boolean ativo);
}