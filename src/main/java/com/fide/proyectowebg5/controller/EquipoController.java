package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fide.proyectowebg5.model.Equipo;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.EquipoService;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoService equipoService;
    private final EstadoService estadoService;
    private final UsuarioService usuarioService;

    public EquipoController(
            EquipoService equipoService,
            EstadoService estadoService,
            UsuarioService usuarioService
    ) {

        this.equipoService = equipoService;
        this.estadoService = estadoService;
        this.usuarioService = usuarioService;
    }

    private boolean esAdmin(HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        return usuario != null &&
                usuario.getRol().equals("ADMIN");
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "equipos",
                equipoService.listar()
        );

        return "equipos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(
            Model model,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/equipos";
        }

        model.addAttribute(
                "equipo",
                new Equipo()
        );

        cargarCatalogos(model);

        return "equipos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/equipos";
        }

        Equipo equipo =
                equipoService.buscarPorId(id);

        if (equipo == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "El equipo seleccionado no existe."
            );

            return "redirect:/equipos";
        }

        model.addAttribute(
                "equipo",
                equipo
        );

        cargarCatalogos(model);

        return "equipos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Equipo equipo,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/equipos";
        }

        if (equipo.getIdEquipo() == null) {

            bindingResult.rejectValue(
                    "idEquipo",
                    "idEquipo.obligatorio",
                    "El ID del equipo es obligatorio."
            );
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            return "equipos/formulario";
        }

        try {

            boolean esNuevo =
                    equipoService.buscarPorId(
                            equipo.getIdEquipo()
                    ) == null;

            if (esNuevo) {
                equipoService.insertar(equipo);
            } else {
                equipoService.actualizar(equipo);
            }

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    esNuevo
                            ? "Equipo registrado correctamente."
                            : "Equipo actualizado correctamente."
            );

            return "redirect:/equipos";

        } catch (Exception e) {

            cargarCatalogos(model);

            model.addAttribute(
                    "error",
                    "No fue posible guardar el equipo."
            );

            return "equipos/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/equipos";
        }

        try {

            equipoService.eliminar(
                    id,
                    2L
            );

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Equipo inactivado correctamente."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible inactivar el equipo."
            );
        }

        return "redirect:/equipos";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

        model.addAttribute(
                "usuarios",
                usuarioService.listar()
        );
    }

}
