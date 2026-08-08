package com.fide.proyectowebg5.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.CanchaService;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.HorarioService;
import com.fide.proyectowebg5.service.ReservaService;
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final HorarioService horarioService;
    private final EstadoService estadoService;
    private final CanchaService canchaService;

    public ReservaController(
            ReservaService reservaService,
            UsuarioService usuarioService,
            HorarioService horarioService,
            EstadoService estadoService,
            CanchaService canchaService) {

        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.horarioService = horarioService;
        this.estadoService = estadoService;
        this.canchaService = canchaService;
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

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/";
        }

        if ("ADMIN".equals(usuario.getRol())) {

            model.addAttribute(
                    "reservas",
                    reservaService.listar()
            );

        } else {

            model.addAttribute(
                    "reservas",
                    reservaService.listarPorUsuario(
                            usuario.getIdUsuario()
                    )
            );
        }

        return "reservas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(
            Model model,
            HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/";
        }

        Reserva reserva = new Reserva();

        // Si es cliente la reserva queda asociada al usuario logueado
        if ("CLIENTE".equals(usuario.getRol())) {
            reserva.setIdUsuario(usuario.getIdUsuario());
        }

        model.addAttribute(
                "reserva",
                reserva
        );

        cargarCatalogos(model);

        return "reservas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute Reserva reserva,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/";
        }

        // Si es cliente la reserva pertenece al usuario logueado
        if ("CLIENTE".equals(usuario.getRol())) {

            reserva.setIdUsuario(
                    usuario.getIdUsuario()
            );
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            return "reservas/formulario";
        }

        try {

            reservaService.insertar(reserva);

            return "redirect:/reservas";

        } catch (Exception e) {

            cargarCatalogos(model);

            if (e.getMessage() != null &&
                    e.getMessage().contains("ORA-20001")) {

                model.addAttribute(
                        "error",
                        "La cancha ya se encuentra reservada para la fecha y horario seleccionado."
                );

            } else {

                model.addAttribute(
                        "error",
                        "No fue posible guardar la reserva."
                );
            }

            return "reservas/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/reservas";
        }

        Reserva reserva =
                reservaService.buscarPorId(id);

        if (reserva == null) {
            return "redirect:/reservas";
        }

        model.addAttribute(
                "reserva",
                reserva
        );

        cargarCatalogos(
                model,
                reserva.getIdHorario()
        );

        return "reservas/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @Valid @ModelAttribute Reserva reserva,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/reservas";
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(
                    model,
                    reserva.getIdHorario()
            );

            return "reservas/formulario";
        }

        try {

            reservaService.actualizar(reserva);

            return "redirect:/reservas";

        } catch (Exception e) {

            cargarCatalogos(
                    model,
                    reserva.getIdHorario()
            );

            if (e.getMessage() != null &&
                    e.getMessage().contains("ORA-20001")) {

                model.addAttribute(
                        "error",
                        "La cancha ya se encuentra reservada para la fecha y horario seleccionado."
                );

            } else {

                model.addAttribute(
                        "error",
                        "No fue posible actualizar la reserva."
                );
            }

            return "reservas/formulario";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/reservas";
        }

        reservaService.eliminar(id);

        return "redirect:/reservas";
    }

    private void cargarCatalogos(Model model) {

        cargarCatalogos(model, null);
    }

    private void cargarCatalogos(
            Model model,
            Long idHorarioActual) {

        model.addAttribute(
                "usuarios",
                usuarioService.listar()
        );

        // Solo se toman las canchas disponibles
        Set<Long> canchasDisponibles =
                canchaService.listar()
                        .stream()
                        .filter(cancha ->
                                Long.valueOf(6L).equals(cancha.getIdEstado()))
                        .map(cancha ->
                                cancha.getIdCancha())
                        .collect(Collectors.toSet());

        /*
         * Al crear solo aparecen horarios de canchas disponibles.
         * Al editar tambien se mantiene visible el horario actual
         * aunque la cancha haya pasado a mantenimiento.
         */
        model.addAttribute(
                "horarios",
                horarioService.listar()
                        .stream()
                        .filter(horario ->
                                canchasDisponibles.contains(
                                        horario.getIdCancha())
                                ||
                                (idHorarioActual != null &&
                                 idHorarioActual.equals(
                                         horario.getIdHorario())))
                        .toList()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );
    }
}