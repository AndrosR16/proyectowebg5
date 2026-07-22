package com.fide.proyectowebg5.service;

import com.fide.proyectowebg5.model.Horario;
import com.fide.proyectowebg5.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {

    private final HorarioRepository repository;

    public HorarioService(HorarioRepository repository) {
        this.repository = repository;
    }

    public List<Horario> listar() {
        return repository.listar();
    }

    public Horario buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public void guardar(Horario horario) {

        if (repository.buscarPorId(horario.getIdHorario()) == null) {
            repository.insertar(horario);
        } else {
            repository.actualizar(horario);
        }

    }

    public void eliminar(Long id) {
        repository.eliminar(id);
    }

}