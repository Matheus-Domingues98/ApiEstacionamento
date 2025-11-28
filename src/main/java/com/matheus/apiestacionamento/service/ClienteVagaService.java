package com.matheus.apiestacionamento.service;

import com.matheus.apiestacionamento.entities.ClienteVaga;
import com.matheus.apiestacionamento.repositories.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {

    private final ClienteVagaRepository clienteVagaRepository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga obj) {
        return clienteVagaRepository.save(obj);
    }
}
