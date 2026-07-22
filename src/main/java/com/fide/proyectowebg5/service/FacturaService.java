package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Factura;
import com.fide.proyectowebg5.repository.FacturaRepository;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> listar() {
        return facturaRepository.listar();
    }

    public Factura buscarPorId(Long idFactura) {
        return facturaRepository.buscarPorId(idFactura);
    }

    public void insertar(Factura factura) {
        facturaRepository.insertar(factura);
    }

    public void actualizar(Factura factura) {
        facturaRepository.actualizar(factura);
    }

    public void eliminar(Long idFactura) {
        facturaRepository.eliminar(idFactura);
    }
}