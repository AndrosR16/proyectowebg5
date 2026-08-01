package com.fide.proyectowebg5.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long totalUsuariosActivos() {
        return jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_USUARIOS_ACTIVOS_FN FROM DUAL",
                Long.class
        );
    }

    public Long totalCanchasActivas() {
        return jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_CANCHAS_ACTIVAS_FN FROM DUAL",
                Long.class
        );
    }

    public Long totalReservas() {
        return jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_RESERVAS_FN FROM DUAL",
                Long.class
        );
    }

    public Long totalIngresos() {
        return jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_INGRESOS_MES_FN FROM DUAL",
                Long.class
        );
    }
}