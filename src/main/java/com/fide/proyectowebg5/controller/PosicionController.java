package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fide.proyectowebg5.model.Posicion;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.PosicionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/posiciones")
public class PosicionController {


    private final PosicionService posicionService;


    public PosicionController(
            PosicionService posicionService
    ) {

        this.posicionService = posicionService;
    }


    private boolean esAdmin(HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        return usuario != null &&
                usuario.getRol().equals("ADMIN");
    }



    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "posiciones",
                posicionService.listar()
        );

        return "posiciones/listar";
    }



    @GetMapping("/nuevo")
    public String mostrarFormulario(
            Model model,
            HttpSession session
    ) {

        if (!esAdmin(session)) {
            return "redirect:/posiciones";
        }


        model.addAttribute(
                "posicion",
                new Posicion()
        );


        return "posiciones/formulario";
    }



    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {


        if (!esAdmin(session)) {
            return "redirect:/posiciones";
        }


        Posicion posicion =
                posicionService.buscarPorId(id);


        if (posicion == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "La posición seleccionada no existe."
            );

            return "redirect:/posiciones";
        }


        model.addAttribute(
                "posicion",
                posicion
        );


        return "posiciones/formulario";
    }



    @PostMapping("/guardar")
    public String guardar(
            @Valid Posicion posicion,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {


        if (!esAdmin(session)) {
            return "redirect:/posiciones";
        }



        if (posicion.getIdPosicion() == null) {

            bindingResult.rejectValue(
                    "idPosicion",
                    "idPosicion.obligatorio",
                    "El ID de la posición es obligatorio."
            );
        }



        if (bindingResult.hasErrors()) {

            return "posiciones/formulario";
        }



        try {


            boolean esNueva =
                    posicionService.buscarPorId(
                            posicion.getIdPosicion()
                    ) == null;



            if (esNueva) {

                posicionService.insertar(posicion);

            } else {

                posicionService.actualizar(posicion);

            }



            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    esNueva
                            ? "Posición registrada correctamente."
                            : "Posición actualizada correctamente."
            );


            return "redirect:/posiciones";


        } catch (Exception e) {


            model.addAttribute(
                    "error",
                    "No fue posible guardar la posición."
            );


            return "posiciones/formulario";
        }
    }



    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {


        if (!esAdmin(session)) {
            return "redirect:/posiciones";
        }


        try {


            posicionService.eliminar(id);


            redirectAttributes.addFlashAttribute(
                    "mensaje",
                    "Posición eliminada correctamente."
            );


        } catch (Exception e) {


            redirectAttributes.addFlashAttribute(
                    "error",
                    "No fue posible eliminar la posición."
            );
        }



        return "redirect:/posiciones";
    }

}
