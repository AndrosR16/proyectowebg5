package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Estado;

import oracle.jdbc.OracleTypes;

@Repository
public class EstadoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstadoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Estado> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_ESTADO_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Estado> estados = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Estado estado = new Estado();

                            estado.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            estado.setDescripcion(
                                    resultado.getString("DESCRIPCION")
                            );

                            estados.add(estado);
                        }
                    }

                    return estados;
                }
        );
    }
}