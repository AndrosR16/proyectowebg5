package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Estado;
import com.fide.proyectowebg5.service.EstadoService;

@Controller
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("estados", estadoService.listar());

        return "estados/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("estado", new Estado());

        return "estados/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Estado estado) {

        estadoService.insertar(estado);

        return "redirect:/estados";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Estado estado = estadoService.buscarPorId(id);

        if (estado == null) {
            return "redirect:/estados";
        }

        model.addAttribute("estado", estado);

        return "estados/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Estado estado) {

        estadoService.actualizar(estado);

        return "redirect:/estados";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        estadoService.eliminar(id);

        return "redirect:/estados";
    }
}