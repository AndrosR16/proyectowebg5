package com.fide.proyectowebg5.controller;

import com.fide.proyectowebg5.model.Estado;
import com.fide.proyectowebg5.model.SuperficieCancha;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.SuperficieCanchaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/superficies")
public class SuperficieCanchaController {

    private final SuperficieCanchaService superficieService;
    private final EstadoService estadoService;

    public SuperficieCanchaController(
            SuperficieCanchaService superficieService,
            EstadoService estadoService) {

        this.superficieService = superficieService;
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("superficies", superficieService.listar());

        return "superficies/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        SuperficieCancha superficie = new SuperficieCancha();
        superficie.setIdEstado(1L);

        model.addAttribute("superficie", superficie);
        model.addAttribute("estados", estadoService.listar());

        return "superficies/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute SuperficieCancha superficie) {

        superficieService.insertar(superficie);

        return "redirect:/superficies";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        SuperficieCancha superficie = superficieService
                .buscarPorId(id)
                .orElseThrow();

        model.addAttribute("superficie", superficie);
        model.addAttribute("estados", estadoService.listar());

        return "superficies/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute SuperficieCancha superficie) {

        superficieService.actualizar(superficie);

        return "redirect:/superficies";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        superficieService.eliminar(id);

        return "redirect:/superficies";
    }

}