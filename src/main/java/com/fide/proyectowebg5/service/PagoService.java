package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Factura;
import com.fide.proyectowebg5.model.Pago;
import com.fide.proyectowebg5.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public List<Pago> listar() {
        return pagoRepository.listar();
    }

    public Pago buscarPorId(Long id) {
        return pagoRepository.buscarPorId(id);
    }

    public void insertar(Pago pago) {
        pagoRepository.insertar(pago);
    }

    public void insertarConFactura(Pago pago, Factura factura) {
        pagoRepository.insertarConFactura(pago, factura);
    }

    public void actualizar(Pago pago) {
        pagoRepository.actualizar(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.eliminar(id);
    }
}