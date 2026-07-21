package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fide.proyectowebg5.model.Cancha;
import com.fide.proyectowebg5.service.CanchaService;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.SuperficieCanchaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/canchas")
public class CanchaController {

    private final CanchaService canchaService;
    private final EstadoService estadoService;
    private final SuperficieCanchaService superficieService;

    public CanchaController(
            CanchaService canchaService,
            EstadoService estadoService,
            SuperficieCanchaService superficieService
    ) {
        this.canchaService = canchaService;
        this.estadoService = estadoService;
        this.superficieService = superficieService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "canchas",
                canchaService.listar()
        );

        return "canchas/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {

        model.addAttribute(
                "cancha",
                new Cancha()
        );

        cargarCatalogos(model);

        return "canchas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        Cancha cancha = canchaService.buscarPorId(id);

        if (cancha == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "La cancha seleccionada no existe."
            );

            return "redirect:/canchas";
        }

        model.addAttribute(
                "cancha",
                cancha
        );

        cargarCatalogos(model);

        return "canchas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Cancha cancha,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (cancha.getIdCancha() == null) {

            bindingResult.rejectValue(
                    "idCancha",
                    "idCancha.obligatorio",
                    "El ID de la cancha es obligatorio."
            );
        }

        if (bindingResult.hasErrors()) {

            cargarCatalogos(model);

            return "canchas/formulario";
        }

        try {

            boolean esNueva = canchaService.buscarPorId(
                    cancha.getIdCancha()
            ) == null;

            canchaService.guardar(cancha);

            if (esNueva) {

                redirectAttributes.addFlashAttribute(
                        "mensaje",
                        "La cancha fue registrada correctamente."
                );

            } else {

                redirectAttributes.addFlashAttribute(
                        "mensaje",
                        "La cancha fue actualizada correctamente."
                );
            }

            return "redirect:/canchas";

        } catch (Exception e) {

            cargarCatalogos(model);

            model.addAttribute(
                    "error",
                    "No fue posible guardar la cancha. Verifique los datos ingresados."
            );

            return "canchas/formulario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {

        try {

            canchaService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "La cancha fue inactivada correctamente."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible inactivar la cancha."
            );
        }

        return "redirect:/canchas";
    }

    private void cargarCatalogos(Model model) {

        model.addAttribute(
                "estados",
                estadoService.listar()
        );

        model.addAttribute(
                "superficies",
                superficieService.listar()
        );
    }
}