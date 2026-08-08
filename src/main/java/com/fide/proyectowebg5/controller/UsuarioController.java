package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EstadoService estadoService;

    public UsuarioController(
            UsuarioService usuarioService,
            EstadoService estadoService) {

        this.usuarioService = usuarioService;
        this.estadoService = estadoService;
    }

    private boolean esAdmin(HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        return usuario != null &&
                "ADMIN".equals(usuario.getRol());
    }

    @GetMapping
    public String listar(
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        model.addAttribute(
                "usuarios",
                usuarioService.listar()
        );

        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        Usuario usuario = new Usuario();

        // Los usuarios nuevos se crean activos y como clientes
        usuario.setIdEstado(1L);
        usuario.setRol("CLIENTE");

        model.addAttribute(
                "usuario",
                usuario
        );

        cargarCatalogos(model);

        model.addAttribute(
                "titulo",
                "Nuevo usuario"
        );

        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Usuario usuario,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        // Al crear la contraseña si es obligatoria
        if (usuario.getContrasena() == null ||
                usuario.getContrasena().isBlank()) {

            bindingResult.rejectValue(
                    "contrasena",
                    "contrasena.obligatoria",
                    "La contraseña es obligatoria."
            );
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            model.addAttribute(
                    "titulo",
                    "Nuevo usuario"
            );

            return "usuarios/formulario";
        }

        usuarioService.guardar(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        Usuario usuario =
                usuarioService.buscarPorId(id);

        if (usuario == null) {
            return "redirect:/usuarios";
        }

        /*
         * No se muestra la contraseña actual.
         * Si se deja vacia al editar, se mantiene la que ya tenia.
         */
        usuario.setContrasena(null);

        model.addAttribute(
                "usuario",
                usuario
        );

        cargarCatalogos(model);

        model.addAttribute(
                "titulo",
                "Editar usuario"
        );

        return "usuarios/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @Valid Usuario usuario,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            model.addAttribute(
                    "titulo",
                    "Editar usuario"
            );

            return "usuarios/formulario";
        }

        usuarioService.actualizar(usuario);

        return "redirect:/usuarios";
    }

    @GetMapping("/inactivar/{id}")
    public String inactivar(
            @PathVariable Long id,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/";
        }

        usuarioService.inactivar(id);

        return "redirect:/usuarios";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "estados",
                estadoService.listar()
        );
    }
}