package br.com.alura.comex.service;

import br.com.alura.comex.dto.ClienteCreateDto;
import br.com.alura.comex.dto.ClienteResponseDto;
import br.com.alura.comex.exception.EntidadeNaoEncontradaException;
import br.com.alura.comex.mapper.ClienteMapper;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.repository.ClienteRepository;
import br.com.alura.comex.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteResponseDto criar(ClienteCreateDto dto) {
        log.info("Iniciando cadastro de cliente para o CPF: {}", dto.getCpf());

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com o CPF: " + dto.getCpf());
        }

        Cliente cliente = clienteMapper.toEntity(dto);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        log.info("Cliente criado com sucesso. ID: {}", clienteSalvo.getId());
        return clienteMapper.toResponseDto(clienteSalvo);
    }

    public List<ClienteResponseDto> listarTodos() {
        log.info("Listando todos os clientes ativos");
        return clienteRepository.findByAtivo(true).stream()
                .map(clienteMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ClienteResponseDto buscarPorId(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findByIdAndAtivo(id, true)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado com ID: " + id));
        return clienteMapper.toResponseDto(cliente);
    }

    public ClienteResponseDto associarUsuario(Long clienteId, Long usuarioId) {
        log.info("Associando cliente ID {} ao usuário ID {}", clienteId, usuarioId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado com ID: " + clienteId));

        if (cliente.getUsuario() != null) {
            throw new RuntimeException("Este cliente já possui um usuário associado.");
        }

        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com ID: " + usuarioId));

        if (clienteRepository.existsByUsuarioId(usuarioId)) {
            throw new RuntimeException("Este usuário já está associado a outro cliente.");
        }
        
        cliente.setUsuario(usuario);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        log.info("Associação realizada com sucesso.");
        return clienteMapper.toResponseDto(clienteSalvo);
    }
}