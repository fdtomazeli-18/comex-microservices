package br.com.alura.comex.service;

import br.com.alura.comex.dto.CategoriaCreateDto;
import br.com.alura.comex.dto.CategoriaResponseDto;
import br.com.alura.comex.dto.CategoriaUpdateDto;
import br.com.alura.comex.exception.CategoriaJaExisteException;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.mapper.CategoriaMapper;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private CategoriaMapper categoriaMapper;

    public CategoriaResponseDto criar(CategoriaCreateDto dto) {
        log.info("Iniciando processo de criação da categoria com nome: {}", dto.getNome());
        
        validarNomeDuplicado(dto.getNome());
        
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria salva = categoriaRepository.save(categoria);
        
        log.info("Categoria criada com sucesso. ID: {}", salva.getId());
        return categoriaMapper.toResponseDto(salva);
    }

    public CategoriaResponseDto atualizar(Long id, CategoriaUpdateDto dto) {
        log.info("Iniciando processo de atualização da categoria ID: {} com nome: {}", id, dto.getNome());
        
        Categoria categoria = buscarCategoriaAtivaPorId(id);
        
        if (!categoria.getNome().equalsIgnoreCase(dto.getNome())) {
            validarNomeDuplicado(dto.getNome());
        }
        
        categoriaMapper.updateEntity(categoria, dto);
        Categoria salva = categoriaRepository.save(categoria);
        
        log.info("Categoria atualizada com sucesso. ID: {}", salva.getId());
        return categoriaMapper.toResponseDto(salva);
    }

    public void deletar(Long id) {
        log.info("Iniciando soft delete da categoria ID: {}", id);
        
        Categoria categoria = buscarCategoriaAtivaPorId(id);
        
        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
        
        log.info("Categoria desativada com sucesso. ID: {}", id);
    }

    public CategoriaResponseDto reativar(Long id) {
        log.info("Iniciando reativação da categoria ID: {}", id);
        
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Categoria não encontrada para reativação com ID: " + id));
        
        categoria.setAtivo(true);
        Categoria salva = categoriaRepository.save(categoria);
        
        log.info("Categoria reativada com sucesso. ID: {}", salva.getId());
        return categoriaMapper.toResponseDto(salva);
    }

    public List<CategoriaResponseDto> listarTodas() {
        log.info("Listando todas as categorias ativas");
        return categoriaRepository.findByAtivo(true).stream()
                .map(categoriaMapper::toResponseDto)
                .toList();
    }
    
    public CategoriaResponseDto buscarPorId(Long id) {
        log.info("Buscando categoria por ID: {}", id);
        Categoria categoria = buscarCategoriaAtivaPorId(id);
        return categoriaMapper.toResponseDto(categoria);
    }
    
    public Page<CategoriaResponseDto> listarComPaginacao(Pageable pageable) {
        log.info("Listando categorias com paginação: {}", pageable);
        return categoriaRepository.findByAtivo(true, pageable)
                .map(categoriaMapper::toResponseDto);
    }

    
    private Categoria buscarCategoriaAtivaPorId(Long id) {
        return categoriaRepository.findByIdAndAtivo(id, true)
                .orElseThrow(() -> {
                    log.warn("Categoria não encontrada ou inativa com ID: {}", id);
                    return new EntidadeNaoEncontradaException("Categoria não encontrada com ID: " + id);
                });
    }

    private void validarNomeDuplicado(String nome) {
        if (categoriaRepository.existsByNomeAndAtivo(nome, true)) {
            log.warn("Tentativa de criar/atualizar categoria com nome já existente: {}", nome);
            throw new CategoriaJaExisteException("Categoria com nome '" + nome + "' já existe");
        }
    }
}