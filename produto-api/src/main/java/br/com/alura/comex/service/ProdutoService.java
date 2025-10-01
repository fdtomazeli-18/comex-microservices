package br.com.alura.comex.service;

import br.com.alura.comex.dto.ProdutoCreateDto;
import br.com.alura.comex.dto.ProdutoResponseDto;
import br.com.alura.comex.dto.ProdutoUpdateDto;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.mapper.ProdutoMapper;
import br.com.alura.comex.model.*;
import lombok.extern.slf4j.Slf4j;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProdutoMapper produtoMapper;

    public ProdutoResponseDto criar(ProdutoCreateDto dto) {
        log.info("Iniciando processo de criação do produto: {}", dto.getNome());
        
        Categoria categoria = buscarCategoriaAtivaPorId(dto.getCategoriaId());
        
        Produto produto = produtoMapper.toEntity(dto, categoria);
        Produto salvo = produtoRepository.save(produto);
        
        log.info("Produto criado com sucesso. ID: {}", salvo.getId());
        return produtoMapper.toResponseDto(salvo);
    }

    public ProdutoResponseDto atualizar(Long id, ProdutoUpdateDto dto) {
        log.info("Iniciando processo de atualização do produto ID: {}", id);
        
        Produto produto = buscarProdutoAtivoPorId(id);
        Categoria categoria = buscarCategoriaAtivaPorId(dto.getCategoriaId());
        
        produtoMapper.updateEntity(produto, dto, categoria);
        Produto salvo = produtoRepository.save(produto);
        
        log.info("Produto atualizado com sucesso. ID: {}", salvo.getId());
        return produtoMapper.toResponseDto(salvo);
    }

    public void deletar(Long id) {
        log.info("Iniciando soft delete do produto ID: {}", id);
        
        Produto produto = buscarProdutoAtivoPorId(id);
        
        produto.setAtivo(false);
        produtoRepository.save(produto);
        
        log.info("Produto desativado com sucesso. ID: {}", id);
    }
    
    public ProdutoResponseDto reativar(Long id) {
        log.info("Iniciando reativação do produto ID: {}", id);
        
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado para reativação com ID: " + id));
        
        produto.setAtivo(true);
        Produto salvo = produtoRepository.save(produto);
        
        log.info("Produto reativado com sucesso. ID: {}", id);
        return produtoMapper.toResponseDto(salvo);
    }

    public List<ProdutoResponseDto> listarTodos() {
        log.info("Listando todos os produtos ativos");
        return produtoRepository.findByAtivo(true).stream()
                .map(produtoMapper::toResponseDto)
                .toList();
    }

    public ProdutoResponseDto buscarPorId(Long id) {
        log.info("Buscando produto por ID: {}", id);
        Produto produto = buscarProdutoAtivoPorId(id);
        return produtoMapper.toResponseDto(produto);
    }
    
    public Page<ProdutoResponseDto> listarComPaginacao(Pageable pageable) {
        log.info("Listando produtos com paginação: {}", pageable);
        return produtoRepository.findByAtivo(true, pageable)
                .map(produtoMapper::toResponseDto);
    }

    private Produto buscarProdutoAtivoPorId(Long id) {
        return produtoRepository.findByIdAndAtivo(id, true)
                .orElseThrow(() -> {
                    log.warn("Produto não encontrado ou inativo com ID: {}", id);
                    return new EntidadeNaoEncontradaException("Produto não encontrado com ID: " + id);
                });
    }

    private Categoria buscarCategoriaAtivaPorId(Long id) {
        return categoriaRepository.findByIdAndAtivo(id, true)
                .orElseThrow(() -> {
                    log.warn("Categoria não encontrada ou inativa com ID: {}", id);
                    return new EntidadeNaoEncontradaException("Categoria não encontrada ou inativa com ID: " + id);
                });
    }
}