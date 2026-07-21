package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.SuperficieCancha;
import com.fide.proyectowebg5.repository.SuperficieCanchaRepository;

@Service
public class SuperficieCanchaService {

    private final SuperficieCanchaRepository superficieRepository;

    public SuperficieCanchaService(
            SuperficieCanchaRepository superficieRepository
    ) {
        this.superficieRepository = superficieRepository;
    }

    public List<SuperficieCancha> listar() {
        return superficieRepository.listar();
    }
}