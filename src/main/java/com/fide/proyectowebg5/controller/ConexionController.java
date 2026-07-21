package com.fide.proyectowebg5.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConexionController {

    private final JdbcTemplate jdbcTemplate;

    public ConexionController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/conexion")
    public String probarConexion(Model model) {

        try {
            String usuario = jdbcTemplate.queryForObject(
                    "SELECT USER FROM DUAL",
                    String.class
            );

            model.addAttribute("conexionExitosa", true);
            model.addAttribute("usuario", usuario);

        } catch (Exception e) {

            Throwable causa = e;

            while (causa.getCause() != null) {
                causa = causa.getCause();
            }

            model.addAttribute("conexionExitosa", false);
            model.addAttribute(
                    "error",
                    causa.getClass().getName() + ": " + causa.getMessage()
            );

            e.printStackTrace();
        }

        return "conexion";
    }
}