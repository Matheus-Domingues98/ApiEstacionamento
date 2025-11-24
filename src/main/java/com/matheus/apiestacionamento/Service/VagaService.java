package com.matheus.apiestacionamento.Service;

import com.matheus.apiestacionamento.entities.Vaga;
import com.matheus.apiestacionamento.exception.CodigoUniqueViolationException;
import com.matheus.apiestacionamento.exception.EntityNotFoundException;
import com.matheus.apiestacionamento.repositories.VagasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class VagaService {

    private final VagasRepository vagasRepository;

    @Transactional
    public Vaga salvar(Vaga obj) {
        try {
            return vagasRepository.save(obj);
        } catch (DataIntegrityViolationException e) {
            throw new CodigoUniqueViolationException(String.format("Vaga com codigo '%s' ja cadastrada", obj.getCodigo()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagasRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com codigo '%s' não foi encontrada ", codigo))
        );
    }
}
