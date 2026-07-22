package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Estado;
import com.fide.proyectowebg5.repository.EstadoRepository;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> listar() {
        return estadoRepository.listar();
    }

    public Estado buscarPorId(Long id) {
        return estadoRepository.buscarPorId(id);
    }

    public void insertar(Estado estado) {
        estadoRepository.insertar(estado);
    }

    public void actualizar(Estado estado) {
        estadoRepository.actualizar(estado);
    }

    public void eliminar(Long id) {
        estadoRepository.eliminar(id);
    }

}