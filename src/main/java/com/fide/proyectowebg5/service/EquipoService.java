package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Equipo;
import com.fide.proyectowebg5.repository.EquipoRepository;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> listar() {
        return equipoRepository.listar();
    }

    public Equipo buscarPorId(Long idEquipo) {
        return equipoRepository.buscarPorId(idEquipo);
    }

    public void insertar(Equipo equipo) {
        equipoRepository.insertar(equipo);
    }

    public void actualizar(Equipo equipo) {
        equipoRepository.actualizar(equipo);
    }

    public void eliminar(Long idEquipo, Long idEstado) {
        equipoRepository.eliminar(idEquipo, idEstado);
    }
}
