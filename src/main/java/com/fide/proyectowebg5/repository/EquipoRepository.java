package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Equipo;

import oracle.jdbc.OracleTypes;

@Repository
public class EquipoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EquipoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Equipo> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_EQUIPO_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Equipo> equipos = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Equipo equipo = new Equipo();

                            equipo.setIdEquipo(
                                    resultado.getLong("ID_EQUIPO")
                            );

                            equipo.setNombreEquipo(
                                    resultado.getString("NOMBRE_EQUIPO")
                            );

                            equipo.setFechaCreacion(
                                    resultado.getDate("FECHA_CREACION").toLocalDate()
                            );

                            equipo.setNombreUsuario(
                                    resultado.getString("NOMBRE") + " "
                                    + resultado.getString("APELLIDO_P") + " "
                                    + resultado.getString("APELLIDO_M")
                            );

                            equipos.add(equipo);
                        }
                    }

                    return equipos;
                }
        );
    }

    public Equipo buscarPorId(Long idEquipo) {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_EQUIPO_BUSCAR_SP(?,?)}"
                    );

                    procedimiento.setLong(
                            1,
                            idEquipo
                    );

                    procedimiento.registerOutParameter(
                            2,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(2)) {

                        if (resultado.next()) {

                            Equipo equipo = new Equipo();

                            equipo.setIdUsuario(
                                    resultado.getLong("ID_USUARIO"));

                            equipo.setIdEstado(
                                    resultado.getLong("ID_ESTADO"));

                            equipo.setIdEquipo(
                                    resultado.getLong("ID_EQUIPO")
                            );

                            equipo.setNombreEquipo(
                                    resultado.getString("NOMBRE_EQUIPO")
                            );

                            equipo.setFechaCreacion(
                                    resultado.getDate("FECHA_CREACION").toLocalDate()
                            );

                            return equipo;
                        }
                    }

                    return null;
                }
        );
    }

    public void insertar(Equipo equipo) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_EQUIPO_INSERT_SP(?,?,?,?,?)}"
                    );

                    procedimiento.setLong(
                            1,
                            equipo.getIdEquipo()
                    );

                    procedimiento.setLong(
                            2,
                            equipo.getIdUsuario()
                    );

                    procedimiento.setString(
                            3,
                            equipo.getNombreEquipo()
                    );

                    procedimiento.setDate(
                            4,
                            Date.valueOf(equipo.getFechaCreacion())
                    );

                    procedimiento.setLong(
                            5,
                            equipo.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void actualizar(Equipo equipo) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_EQUIPO_UPDATE_SP(?,?,?,?,?)}"
                    );

                    procedimiento.setLong(
                            1,
                            equipo.getIdEquipo()
                    );

                    procedimiento.setLong(
                            2,
                            equipo.getIdUsuario()
                    );

                    procedimiento.setString(
                            3,
                            equipo.getNombreEquipo()
                    );

                    procedimiento.setDate(
                            4,
                            Date.valueOf(equipo.getFechaCreacion())
                    );

                    procedimiento.setLong(
                            5,
                            equipo.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long idEquipo, Long idEstado) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_EQUIPO_DELETE_SP(?,?)}"
                    );

                    procedimiento.setLong(
                            1,
                            idEquipo
                    );

                    procedimiento.setLong(
                            2,
                            idEstado
                    );

                    return procedimiento;
                }
        );
    }

}
