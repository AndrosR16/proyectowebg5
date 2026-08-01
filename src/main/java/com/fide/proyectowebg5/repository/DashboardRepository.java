package com.fide.proyectowebg5.repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.CanchaRanking;

import oracle.jdbc.OracleTypes;

@Repository
public class DashboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long totalUsuariosActivos() {

        Long resultado = jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_USUARIOS_ACTIVOS_FN FROM DUAL",
                Long.class
        );

        return resultado != null ? resultado : 0L;
    }

    public Long totalCanchasActivas() {

        Long resultado = jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_CANCHAS_ACTIVAS_FN FROM DUAL",
                Long.class
        );

        return resultado != null ? resultado : 0L;
    }

    public Long totalReservas() {

        Long resultado = jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_RESERVAS_FN FROM DUAL",
                Long.class
        );

        return resultado != null ? resultado : 0L;
    }

    public BigDecimal totalIngresos() {

    BigDecimal resultado = jdbcTemplate.queryForObject(
            "SELECT FIDE_PROYECTO_PCK.FIDE_TOTAL_INGRESOS_FN FROM DUAL",
            BigDecimal.class
    );

    return resultado != null
            ? resultado
            : BigDecimal.ZERO;
}

    public Long contarReservasDia(java.sql.Date fecha) {

        Long resultado = jdbcTemplate.queryForObject(
                "SELECT FIDE_PROYECTO_PCK.FIDE_CONTAR_RESERVAS_DIA_FN(?) FROM DUAL",
                Long.class,
                fecha
        );

        return resultado != null ? resultado : 0L;
    }

    public List<CanchaRanking> canchasMasReservadas() {

        return jdbcTemplate.execute(
                (ConnectionCallback<List<CanchaRanking>>) connection -> {

                    List<CanchaRanking> ranking = new ArrayList<>();

                    try (CallableStatement procedimiento =
                                 connection.prepareCall(
                                         "{call FIDE_PROYECTO_PCK.FIDE_CANCHAS_MAS_RESERVADAS_SP(?)}"
                                 )) {

                        procedimiento.registerOutParameter(
                                1,
                                OracleTypes.CURSOR
                        );

                        procedimiento.execute();

                        try (ResultSet resultado =
                                     (ResultSet) procedimiento.getObject(1)) {

                            while (resultado.next()) {

                                CanchaRanking cancha = new CanchaRanking();

                                cancha.setIdCancha(
                                        resultado.getLong("ID_CANCHA")
                                );

                                cancha.setNombreCancha(
                                        resultado.getString("NOMBRE_CANCHA")
                                );

                                cancha.setTotalReservas(
                                        resultado.getLong("TOTAL_RESERVAS")
                                );

                                ranking.add(cancha);
                            }
                        }
                    }

                    return ranking;
                }
        );
    }
}