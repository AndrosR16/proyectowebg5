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
import com.fide.proyectowebg5.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final DashboardService dashboardService;

    public HomeController(
            UsuarioService usuarioService,
            DashboardService dashboardService) {

        this.usuarioService = usuarioService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String mostrarInicio(
            HttpSession session,
            Model model) {

        if (session.getAttribute("usuario") == null) {
            return "login";
        }

        cargarDashboard(model);

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

        session.setAttribute("usuario", usuario);

        cargarDashboard(model);

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
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