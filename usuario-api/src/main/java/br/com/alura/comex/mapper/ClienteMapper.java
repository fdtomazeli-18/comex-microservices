package br.com.alura.comex.mapper;

import br.com.alura.comex.dto.ClienteCreateDto;
import br.com.alura.comex.dto.ClienteResponseDto;
import br.com.alura.comex.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteCreateDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setLogradouro(dto.getLogradouro());
        cliente.setBairro(dto.getBairro());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());
        cliente.setCep(dto.getCep());
        
        return cliente;
    }

    public ClienteResponseDto toResponseDto(Cliente cliente) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setTelefone(cliente.getTelefone());
        dto.setCidade(cliente.getCidade());
        dto.setEstado(cliente.getEstado());
        dto.setAtivo(cliente.isAtivo());
        
        if (cliente.getUsuario() != null) {
            dto.setEmail(cliente.getUsuario().getEmail());
        }

        return dto;
    }
}