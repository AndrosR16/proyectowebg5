package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.HorarioService;
import com.fide.proyectowebg5.service.ReservaService;
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final HorarioService horarioService;
    private final EstadoService estadoService;

    public ReservaController(
            ReservaService reservaService,
            UsuarioService usuarioService,
            HorarioService horarioService,
            EstadoService estadoService) {

        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.horarioService = horarioService;
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
            @ModelAttribute Reserva reserva,
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

        try {

            reservaService.insertar(reserva);

            return "redirect:/reservas";

        } catch (Exception e) {

            cargarCatalogos(model);

            // Si el horario ya esta reservado se muestra el mensaje en el formulario
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

        cargarCatalogos(model);

        return "reservas/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute Reserva reserva,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/reservas";
        }

        try {

            reservaService.actualizar(reserva);

            return "redirect:/reservas";

        } catch (Exception e) {

            cargarCatalogos(model);

            // No permite mover una reserva a un horario que ya esta ocupado
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

        model.addAttribute(
                "usuarios",
                usuarioService.listar()
        );

        model.addAttribute(
                "horarios",
                horarioService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );
    }
}