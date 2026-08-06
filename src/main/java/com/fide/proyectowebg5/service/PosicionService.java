package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Posicion;
import com.fide.proyectowebg5.repository.PosicionRepository;

@Service
public class PosicionService {

    private final PosicionRepository posicionRepository;


    public PosicionService(PosicionRepository posicionRepository) {
        this.posicionRepository = posicionRepository;
    }


    public List<Posicion> listar() {
        return posicionRepository.listar();
    }


    public Posicion buscarPorId(Long idPosicion) {
        return posicionRepository.buscarPorId(idPosicion);
    }


    public void insertar(Posicion posicion) {
        posicionRepository.insertar(posicion);
    }


    public void actualizar(Posicion posicion) {
        posicionRepository.actualizar(posicion);
    }


    public void eliminar(Long idPosicion) {
        posicionRepository.eliminar(idPosicion);
    }

}
