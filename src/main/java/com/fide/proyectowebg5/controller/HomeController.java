package com.fide.proyectowebg5.controller;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fide.proyectowebg5.model.Usuario;
import com.fide.proyectowebg5.service.DashboardService;
import com.fide.proyectowebg5.service.ReservaService;
import com.fide.proyectowebg5.service.UsuarioService;
import com.fide.proyectowebg5.service.FacturaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {


    private final UsuarioService usuarioService;
    private final DashboardService dashboardService;
    private final ReservaService reservaService;
private final FacturaService facturaService;

    public HomeController(
            UsuarioService usuarioService,
            DashboardService dashboardService,
            ReservaService reservaService,
            FacturaService facturaService) {

        this.usuarioService = usuarioService;
        this.dashboardService = dashboardService;
        this.reservaService = reservaService;
        this.facturaService = facturaService;
    }



    @GetMapping("/")
    public String mostrarInicio(
            HttpSession session,
            Model model) {


        Usuario usuario =
                (Usuario) session.getAttribute("usuario");


        if (usuario == null) {

            return "login";

        }



        if ("ADMIN".equals(usuario.getRol())) {

            cargarDashboard(model);

        } else {

            cargarDashboardUsuario(model, usuario);

        }



        return "index";

    }




    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {


        Usuario usuario =
                usuarioService.login(username, contrasena);



        if (usuario == null) {


            model.addAttribute(
                    "error",
                    "Usuario o contraseña incorrectos."
            );


            return "login";

        }



        session.setAttribute(
                "usuario",
                usuario
        );




        if ("ADMIN".equals(usuario.getRol())) {


            cargarDashboard(model);


        } else {


            cargarDashboardUsuario(model, usuario);


        }



        return "index";

    }


    @GetMapping("/logout")
    public String logout(
            HttpSession session) {


        session.invalidate();


        return "redirect:/";

    }




    private void cargarDashboardUsuario(
            Model model,
            Usuario usuario) {



        int misReservas =

                reservaService
                .listarPorUsuario(usuario.getIdUsuario())
                .size();

                int misFacturas =
            facturaService
            .listarPorUsuario(usuario.getNombre())
            .size();


        model.addAttribute(
                "misReservas",
                misReservas
                
        );

        model.addAttribute(
                "misFacturas",
                misFacturas
        );

model.addAttribute(
    "proximasReservasUsuario",
    reservaService.proximasReservasUsuario(usuario.getIdUsuario())
);

        

    }






    private void cargarDashboard(Model model) {


        model.addAttribute(
                "totalUsuarios",
                dashboardService.totalUsuariosActivos()
        );


        model.addAttribute(
                "totalCanchas",
                dashboardService.totalCanchasActivas()
        );


        model.addAttribute(
                "totalReservas",
                dashboardService.totalReservas()
        );


        model.addAttribute(
                "totalIngresos",
                dashboardService.totalIngresos()
        );



        LocalDate hoy = LocalDate.now();


        LocalDate lunes =
                hoy.with(DayOfWeek.MONDAY);



        model.addAttribute(
                "reservasLunes",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes)
                )
        );



        model.addAttribute(
                "reservasMartes",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(1))
                )
        );



        model.addAttribute(
                "reservasMiercoles",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(2))
                )
        );



        model.addAttribute(
                "reservasJueves",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(3))
                )
        );



        model.addAttribute(
                "reservasViernes",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(4))
                )
        );



        model.addAttribute(
                "reservasSabado",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(5))
                )
        );



        model.addAttribute(
                "reservasDomingo",
                dashboardService.contarReservasDia(
                        Date.valueOf(lunes.plusDays(6))
                )
        );



        model.addAttribute(
                "canchasMasReservadas",
                dashboardService.canchasMasReservadas()
        );



        model.addAttribute(
                "proximasReservas",
                dashboardService.proximasReservas()
        );


    }
    

}