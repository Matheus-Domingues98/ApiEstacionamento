package com.matheus.apiestacionamento.Service;

import com.matheus.apiestacionamento.entities.Cliente;
import com.matheus.apiestacionamento.exception.CpfUniqueViolationException;
import com.matheus.apiestacionamento.exception.EntityNotFoundException;
import com.matheus.apiestacionamento.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private  final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente obj) {
        try {
            return clienteRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException
                    (String.format("CPF '%s' não pode ser cadastrado, ja existe no sistema", obj.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente id=%s nao encontrado não encontrado no sistema", id)));
    }

    @Transactional(readOnly = true)
    public Page<Cliente> buscarTodos(Pageable pageable) {
        return  clienteRepository.findAll(pageable);
    }
}
