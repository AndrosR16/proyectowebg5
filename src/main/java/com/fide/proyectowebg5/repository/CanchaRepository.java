package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Cancha;

import oracle.jdbc.OracleTypes;

@Repository
public class CanchaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CanchaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cancha> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_CANCHA_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Cancha> canchas = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Cancha cancha = new Cancha();

                            cancha.setIdCancha(
                                    resultado.getLong("ID_CANCHA")
                            );

                            cancha.setNombreCancha(
                                    resultado.getString("NOMBRE_CANCHA")
                            );

                            cancha.setCapacidad(
                                    resultado.getInt("CAPACIDAD")
                            );

                            cancha.setPrecioHora(
                                    resultado.getBigDecimal("PRECIO_HORA")
                            );

                            cancha.setIdSuperficie(
                                    resultado.getLong("ID_SUPERFICIE")
                            );

                            cancha.setSuperficie(
                                    resultado.getString("SUPERFICIE")
                            );

                            cancha.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            cancha.setEstado(
                                    resultado.getString("ESTADO")
                            );

                            canchas.add(cancha);
                        }
                    }

                    return canchas;
                }
        );
    }

    public void insertar(Cancha cancha) {

    jdbcTemplate.update(
            connection -> {

                CallableStatement procedimiento = connection.prepareCall(
                        "{call FIDE_PROYECTO_PCK.FIDE_CANCHA_INSERT_SP(?,?,?,?,?,?)}"
                );

                procedimiento.setLong(
                        1,
                        cancha.getIdCancha()
                );

                procedimiento.setString(
                        2,
                        cancha.getNombreCancha()
                );

                procedimiento.setInt(
                        3,
                        cancha.getCapacidad()
                );

                procedimiento.setBigDecimal(
                        4,
                        cancha.getPrecioHora()
                );

                procedimiento.setLong(
                        5,
                        cancha.getIdSuperficie()
                );

                procedimiento.setLong(
                        6,
                        cancha.getIdEstado()
                );

                return procedimiento;
            }
    );
}
    public void actualizar(Cancha cancha) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_CANCHA_UPDATE_SP(?,?,?,?,?,?)}"
                    );

                    procedimiento.setLong(
                            1,
                            cancha.getIdCancha()
                    );

                    procedimiento.setString(
                            2,
                            cancha.getNombreCancha()
                    );

                    procedimiento.setInt(
                            3,
                            cancha.getCapacidad()
                    );

                    procedimiento.setBigDecimal(
                            4,
                            cancha.getPrecioHora()
                    );

                    procedimiento.setLong(
                            5,
                            cancha.getIdSuperficie()
                    );

                    procedimiento.setLong(
                            6,
                            cancha.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long idCancha) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_CANCHA_DELETE_SP(?)}"
                    );

                    procedimiento.setLong(
                            1,
                            idCancha
                    );

                    return procedimiento;
                }
        );
    }
}