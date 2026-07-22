package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fide.proyectowebg5.model.Factura;
import com.fide.proyectowebg5.model.Pago;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.MetodoPagoService;
import com.fide.proyectowebg5.service.PagoService;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final MetodoPagoService metodoPagoService;
    private final EstadoService estadoService;

    public PagoController(
            PagoService pagoService,
            MetodoPagoService metodoPagoService,
            EstadoService estadoService) {

        this.pagoService = pagoService;
        this.metodoPagoService = metodoPagoService;
        this.estadoService = estadoService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "pagos",
                pagoService.listar()
        );

        return "pagos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Pago pago = new Pago();

        // Estado pendiente
        pago.setIdEstado(3L);

        model.addAttribute(
                "pago",
                pago
        );

        cargarCatalogos(model);

        return "pagos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Pago pago) {

        Factura factura = new Factura();

        /*
         * Por ahora se utiliza el mismo número del pago
         * como identificador de la factura.
         */
        factura.setIdFactura(
                pago.getIdPago()
        );

        factura.setIdPago(
                pago.getIdPago()
        );

        factura.setNumeroFactura(
                "FAC-" + String.format(
                        "%05d",
                        pago.getIdPago()
                )
        );

        // Estado activo para la factura
        factura.setIdEstado(1L);

        pagoService.insertarConFactura(
                pago,
                factura
        );

        return "redirect:/facturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Pago pago = pagoService.buscarPorId(id);

        if (pago == null) {
            return "redirect:/pagos";
        }

        model.addAttribute(
                "pago",
                pago
        );

        cargarCatalogos(model);

        return "pagos/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute Pago pago) {

        pagoService.actualizar(pago);

        return "redirect:/pagos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        pagoService.eliminar(id);

        return "redirect:/pagos";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "metodosPago",
                metodoPagoService.listar()
        );

        model.addAttribute(
                "estados",
                estadoService.listar()
                        .stream()
                        .filter(estado ->
                                estado.getIdEstado() != null
                                && (
                                    estado.getIdEstado().longValue() == 3L
                                    || estado.getIdEstado().longValue() == 4L
                                )
                        )
                        .toList()
        );
    }
}