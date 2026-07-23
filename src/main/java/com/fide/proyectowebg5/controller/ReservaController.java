package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.HorarioService;
import com.fide.proyectowebg5.service.ReservaService;
import com.fide.proyectowebg5.service.UsuarioService;

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

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "reservas",
                reservaService.listar()
        );

        return "reservas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "reserva",
                new Reserva()
        );

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

        return "reservas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Reserva reserva) {

        reservaService.insertar(reserva);

        return "redirect:/reservas";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "reserva",
                reservaService.buscarPorId(id)
        );

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

        return "reservas/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute Reserva reserva) {

        reservaService.actualizar(reserva);

        return "redirect:/reservas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        reservaService.eliminar(id);

        return "redirect:/reservas";
    }

}