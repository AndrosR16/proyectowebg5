package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Cancha;
import com.fide.proyectowebg5.repository.CanchaRepository;

@Service
public class CanchaService {

    private final CanchaRepository canchaRepository;

    public CanchaService(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    public List<Cancha> listar() {
        return canchaRepository.listar();
    }

    public Cancha buscarPorId(Long idCancha) {

        return canchaRepository.listar()
                .stream()
                .filter(cancha ->
                        cancha.getIdCancha().equals(idCancha)
                )
                .findFirst()
                .orElse(null);
    }

    public void guardar(Cancha cancha) {

        if (cancha.getIdCancha() == null) {
            throw new IllegalArgumentException(
                    "El ID de la cancha es obligatorio."
            );
        }

        Cancha canchaExistente = buscarPorId(
                cancha.getIdCancha()
        );

        if (canchaExistente == null) {
            canchaRepository.insertar(cancha);
        } else {
            canchaRepository.actualizar(cancha);
        }
    }

    public void eliminar(Long idCancha) {
        canchaRepository.eliminar(idCancha);
    }
}