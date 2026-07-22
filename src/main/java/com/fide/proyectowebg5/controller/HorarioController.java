package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fide.proyectowebg5.model.Horario;
import com.fide.proyectowebg5.service.CanchaService;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.HorarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    private final HorarioService horarioService;
    private final CanchaService canchaService;
    private final EstadoService estadoService;

    public HorarioController(
            HorarioService horarioService,
            CanchaService canchaService,
            EstadoService estadoService
    ) {
        this.horarioService = horarioService;
        this.canchaService = canchaService;
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "horarios",
                horarioService.listar()
        );

        return "horarios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "horario",
                new Horario()
        );

        cargarCatalogos(model);

        return "horarios/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        Horario horario = horarioService.buscarPorId(id);

        if (horario == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "El horario no existe."
            );

            return "redirect:/horarios";
        }

        model.addAttribute(
                "horario",
                horario
        );

        cargarCatalogos(model);

        return "horarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Horario horario,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            return "horarios/formulario";
        }

        try {

            boolean nuevo =
                    horarioService.buscarPorId(
                            horario.getIdHorario()
                    ) == null;

            horarioService.guardar(horario);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    nuevo
                            ? "Horario registrado correctamente."
                            : "Horario actualizado correctamente."
            );

            return "redirect:/horarios";

        } catch (Exception ex) {

            cargarCatalogos(model);

            model.addAttribute(
                    "error",
                    "No fue posible guardar el horario."
            );

            return "horarios/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            horarioService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Horario eliminado correctamente."
            );

        } catch (Exception ex) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible eliminar el horario."
            );
        }

        return "redirect:/horarios";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "canchas",
                canchaService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

    }

}