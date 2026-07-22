package com.fide.proyectowebg5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.SuperficieCancha;
import com.fide.proyectowebg5.repository.SuperficieCanchaRepository;

@Service
public class SuperficieCanchaService {

    private final SuperficieCanchaRepository repository;

    public SuperficieCanchaService(SuperficieCanchaRepository repository) {
        this.repository = repository;
    }

    public List<SuperficieCancha> listar() {
        return repository.listar();
    }

    public Optional<SuperficieCancha> buscarPorId(Long id) {
        return listar()
                .stream()
                .filter(s -> s.getIdSuperficie().equals(id))
                .findFirst();
    }

    public void insertar(SuperficieCancha superficie) {
        repository.insertar(superficie);
    }

    public void actualizar(SuperficieCancha superficie) {
        repository.actualizar(superficie);
    }

    public void eliminar(Long id) {
        repository.eliminar(id);
    }
}