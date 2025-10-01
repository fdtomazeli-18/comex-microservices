package br.com.alura.comex.repository;

import br.com.alura.comex.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    List<Produto> findByAtivo(boolean ativo);
    
    Page<Produto> findByAtivo(boolean ativo, Pageable pageable);
    
    Optional<Produto> findByIdAndAtivo(Long id, boolean ativo);
}