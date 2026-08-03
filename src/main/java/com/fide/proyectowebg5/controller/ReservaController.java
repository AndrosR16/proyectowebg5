package com.fide.proyectowebg5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fide.proyectowebg5.model.Reserva;
import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.EstadoService;
import com.fide.proyectowebg5.service.HorarioService;
import com.fide.proyectowebg5.service.ReservaService;
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import java.util.List;


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






    private boolean esAdmin(HttpSession session){


        Usuario usuario =
                (Usuario) session.getAttribute("usuario");


        return usuario != null &&
                usuario.getRol().equals("ADMIN");
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


        if(usuario == null){
            return "redirect:/";
        }



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
            @ModelAttribute Reserva reserva,
            HttpSession session) {



        Usuario usuario =
                (Usuario) session.getAttribute("usuario");



        if(usuario == null){
            return "redirect:/";
        }





        /*
         * Si es usuario normal,
         * la reserva pertenece automáticamente
         * al usuario logueado.
         */
        if("USUARIO".equals(usuario.getRol())){


            reserva.setIdUsuario(
                    usuario.getIdUsuario()
            );


        }




        reservaService.insertar(reserva);



        return "redirect:/reservas";
    }









    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            HttpSession session) {


        if(!esAdmin(session)){
            return "redirect:/reservas";
        }



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
            @ModelAttribute Reserva reserva,
            HttpSession session) {


        if(!esAdmin(session)){
            return "redirect:/reservas";
        }



        reservaService.actualizar(reserva);



        return "redirect:/reservas";
    }









    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            HttpSession session) {


        if(!esAdmin(session)){
            return "redirect:/reservas";
        }



        reservaService.eliminar(id);



        return "redirect:/reservas";
    }


}