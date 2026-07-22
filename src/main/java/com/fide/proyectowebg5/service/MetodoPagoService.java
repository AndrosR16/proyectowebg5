package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.MetodoPago;
import com.fide.proyectowebg5.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<MetodoPago> listar() {
        return metodoPagoRepository.listar();
    }

    public MetodoPago buscarPorId(Long id) {
        return metodoPagoRepository.buscarPorId(id);
    }

    public void insertar(MetodoPago metodoPago) {
        metodoPagoRepository.insertar(metodoPago);
    }

    public void actualizar(MetodoPago metodoPago) {
        metodoPagoRepository.actualizar(metodoPago);
    }

    public void eliminar(Long id) {
        metodoPagoRepository.eliminar(id);
    }
}