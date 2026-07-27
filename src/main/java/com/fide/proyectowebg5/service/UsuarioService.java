package com.fide.proyectowebg5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.listar();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.guardar(usuario);
    }

    public void actualizar(Usuario usuario) {
        usuarioRepository.actualizar(usuario);
    }

    public void inactivar(Long id) {
        usuarioRepository.inactivar(id);
    }

    public Usuario login(String username, String contrasena) {
        return usuarioRepository.login(username, contrasena);
    }

}