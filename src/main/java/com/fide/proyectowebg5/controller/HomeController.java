package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String mostrarInicio(HttpSession session) {

        if (session.getAttribute("usuario") != null) {
            return "index";
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        Usuario usuario = usuarioService.login(username, contrasena);

        if (usuario == null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }

        session.setAttribute("usuario", usuario);

        return "index";
    }

}