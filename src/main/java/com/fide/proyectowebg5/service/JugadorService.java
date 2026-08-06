package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Jugador;
import com.fide.proyectowebg5.repository.JugadorRepository;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public List<Jugador> listar() {
        return jugadorRepository.listar();
    }

    public Jugador buscarPorId(Long idJugador) {
        return jugadorRepository.buscarPorId(idJugador);
    }

    public void insertar(Jugador jugador) {
        jugadorRepository.insertar(jugador);
    }

    public void actualizar(Jugador jugador) {
        jugadorRepository.actualizar(jugador);
    }

    public void eliminar(Long idJugador, Long idEstado) {
        jugadorRepository.eliminar(idJugador, idEstado);
    }
}
