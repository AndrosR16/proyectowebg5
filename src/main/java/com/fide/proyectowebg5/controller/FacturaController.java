package com.fide.proyectowebg5.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Factura;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.FacturaService;
import com.fide.proyectowebg5.service.PagoService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final PagoService pagoService;
    private final EstadoService estadoService;

    public FacturaController(
            FacturaService facturaService,
            PagoService pagoService,
            EstadoService estadoService) {

        this.facturaService = facturaService;
        this.pagoService = pagoService;
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "facturas",
                facturaService.listar()
        );

        return "facturas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Factura factura = new Factura();

        factura.setFechaFactura(LocalDate.now());
        factura.setIdEstado(1L);

        model.addAttribute(
                "factura",
                factura
        );

        cargarCatalogos(model);

        return "facturas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Factura factura) {

        facturaService.insertar(factura);

        return "redirect:/facturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Factura factura = facturaService.buscarPorId(id);

        if (factura == null) {
            return "redirect:/facturas";
        }

        model.addAttribute(
                "factura",
                factura
        );

        cargarCatalogos(model);

        return "facturas/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute Factura factura) {

        facturaService.actualizar(factura);

        return "redirect:/facturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        facturaService.eliminar(id);

        return "redirect:/facturas";
    }

    @GetMapping("/imprimir/{id}")
    public String imprimir(
        @PathVariable Long id,
        Model model) {

    Factura factura = facturaService.buscarPorId(id);

    if (factura == null) {
        return "redirect:/facturas";
    }

    model.addAttribute(
            "factura",
            factura
    );

    return "facturas/imprimir";
}


    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "pagos",
                pagoService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
                        .stream()
                        .filter(estado ->
                                estado.getIdEstado() != null
                                && (
                                    estado.getIdEstado().longValue() == 1L
                                    || estado.getIdEstado().longValue() == 2L
                                ))
                        .toList()
        );
    }
}