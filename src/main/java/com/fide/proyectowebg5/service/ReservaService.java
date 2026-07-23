package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.repository.ReservaRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listar() {
        return reservaRepository.listar();
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.buscarPorId(id);
    }

    public void insertar(Reserva reserva) {
        reservaRepository.insertar(reserva);
    }

    public void actualizar(Reserva reserva) {
        reservaRepository.actualizar(reserva);
    }

    public void eliminar(Long id) {
        reservaRepository.eliminar(id);
    }
}