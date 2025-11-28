package com.matheus.apiestacionamento.service;

import com.matheus.apiestacionamento.entities.Cliente;
import com.matheus.apiestacionamento.entities.ClienteVaga;
import com.matheus.apiestacionamento.entities.StatusVaga;
import com.matheus.apiestacionamento.entities.Vaga;
import com.matheus.apiestacionamento.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkIn(ClienteVaga obj) {
        Cliente cliente = clienteService.buscaPorCpf(obj.getCliente().getCpf());
        obj.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatusVaga(StatusVaga.OCUPADO);
        obj.setVaga(vaga);

        obj.setDataEntrada(LocalDateTime.now());
        obj.setRecibo(EstacionamentoUtils.generateRecibo());

        return clienteVagaService.salvar(obj);
    }
}
