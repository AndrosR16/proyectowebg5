package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.UsuarioService;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
public String registrarUsuario(

        @RequestParam String nombre,
        @RequestParam String apellidoP,
        @RequestParam(required = false) String apellidoM,
        @RequestParam String username,
        @RequestParam String contrasena,
        @RequestParam String confirmarContrasena,
        Model model) {

    // Validar que las contraseñas coincidan
    if (!contrasena.equals(confirmarContrasena)) {

        model.addAttribute("error", "Las contraseñas no coinciden.");

        return "registro";
    }

    Usuario usuario = new Usuario();

    usuario.setNombre(nombre);
    usuario.setApellidoP(apellidoP);
    usuario.setApellidoM(apellidoM);
    usuario.setUsername(username);
    usuario.setContrasena(contrasena);

    // Valores automáticos
    usuario.setRol("USUARIO");
    usuario.setIdEstado(1L);

    usuarioService.guardar(usuario);

    return "redirect:/";
}

}