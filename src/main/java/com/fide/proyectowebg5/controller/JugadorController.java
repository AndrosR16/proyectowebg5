package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fide.proyectowebg5.model.Jugador;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.JugadorService;
import com.fide.proyectowebg5.service.PosicionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final EstadoService estadoService;
    private final PosicionService posicionService;

    public JugadorController(
            JugadorService jugadorService,
            EstadoService estadoService,
            PosicionService posicionService
    ) {

        this.jugadorService = jugadorService;
        this.estadoService = estadoService;
        this.posicionService = posicionService;
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
                "jugadores",
                jugadorService.listar()
        );

        return "jugadores/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(
            Model model,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/jugadores";
        }

        model.addAttribute(
                "jugador",
                new Jugador()
        );

        cargarCatalogos(model);

        return "jugadores/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/jugadores";
        }

        Jugador jugador =
                jugadorService.buscarPorId(id);

        if (jugador == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "El jugador seleccionado no existe."
            );

            return "redirect:/jugadores";
        }

        model.addAttribute(
                "jugador",
                jugador
        );

        cargarCatalogos(model);

        return "jugadores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Jugador jugador,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/jugadores";
        }

        if (jugador.getIdJugador() == null) {

            bindingResult.rejectValue(
                    "idJugador",
                    "idJugador.obligatorio",
                    "El ID del jugador es obligatorio."
            );
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            return "jugadores/formulario";
        }

        try {

            boolean esNuevo =
                    jugadorService.buscarPorId(
                            jugador.getIdJugador()
                    ) == null;

            if (esNuevo) {
                jugadorService.insertar(jugador);
            } else {
                jugadorService.actualizar(jugador);
            }

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    esNuevo
                            ? "Jugador registrado correctamente."
                            : "Jugador actualizado correctamente."
            );

            return "redirect:/jugadores";

        } catch (Exception e) {

            cargarCatalogos(model);

            model.addAttribute(
                    "error",
                    "No fue posible guardar el jugador."
            );

            return "jugadores/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/jugadores";
        }

        try {

            jugadorService.eliminar(
                    id,
                    2L
            );

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Jugador inactivado correctamente."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible inactivar el jugador."
            );
        }

        return "redirect:/jugadores";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "posiciones",
                posicionService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );
    }

}