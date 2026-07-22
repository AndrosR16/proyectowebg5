package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.MetodoPago;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.MetodoPagoService;

@Controller
@RequestMapping("/metodos-pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;
    private final EstadoService estadoService;

    public MetodoPagoController(
            MetodoPagoService metodoPagoService,
            EstadoService estadoService) {

        this.metodoPagoService = metodoPagoService;
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "metodosPago",
                metodoPagoService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

        return "metodos-pago/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "metodoPago",
                new MetodoPago()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

        return "metodos-pago/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute MetodoPago metodoPago) {

        metodoPagoService.insertar(metodoPago);

        return "redirect:/metodos-pago";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        MetodoPago metodoPago =
                metodoPagoService.buscarPorId(id);

        if (metodoPago == null) {
            return "redirect:/metodos-pago";
        }

        model.addAttribute(
                "metodoPago",
                metodoPago
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

        return "metodos-pago/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute MetodoPago metodoPago) {

        metodoPagoService.actualizar(metodoPago);

        return "redirect:/metodos-pago";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        metodoPagoService.eliminar(id);

        return "redirect:/metodos-pago";
    }
}