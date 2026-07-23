package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Reserva;

import oracle.jdbc.OracleTypes;

@Repository
public class ReservaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reserva> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_RESERVA_LISTAR_SP(?)}"
                            );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Reserva> reservas = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Reserva reserva = new Reserva();

                            reserva.setIdReserva(
                                    resultado.getLong("ID_RESERVA")
                            );

                            reserva.setIdUsuario(
                                    resultado.getLong("ID_USUARIO")
                            );

                            reserva.setNombreUsuario(
                                    resultado.getString("NOMBRE_USUARIO")
                            );

                            reserva.setIdHorario(
                                    resultado.getLong("ID_HORARIO")
                            );

                            reserva.setDescripcionHorario(
                                    resultado.getString("DESCRIPCION_HORARIO")
                            );

                            reserva.setFechaReserva(
                                    resultado.getDate("FECHA_RESERVA").toLocalDate()
                            );

                            reserva.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            reserva.setNombreEstado(
                                    resultado.getString("NOMBRE_ESTADO")
                            );

                            reservas.add(reserva);
                        }
                    }

                    return reservas;
                }
        );
    }

    public Reserva buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(reserva -> reserva.getIdReserva().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void insertar(Reserva reserva) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_RESERVA_INSERT_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            reserva.getIdReserva()
                    );

                    procedimiento.setLong(
                            2,
                            reserva.getIdUsuario()
                    );

                    procedimiento.setLong(
                            3,
                            reserva.getIdHorario()
                    );

                    procedimiento.setDate(
                            4,
                            java.sql.Date.valueOf(reserva.getFechaReserva())
                    );

                    procedimiento.setLong(
                            5,
                            reserva.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void actualizar(Reserva reserva) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_RESERVA_UPDATE_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            reserva.getIdReserva()
                    );

                    procedimiento.setLong(
                            2,
                            reserva.getIdUsuario()
                    );

                    procedimiento.setLong(
                            3,
                            reserva.getIdHorario()
                    );

                    procedimiento.setDate(
                            4,
                            java.sql.Date.valueOf(reserva.getFechaReserva())
                    );

                    procedimiento.setLong(
                            5,
                            reserva.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long id) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_RESERVA_DELETE_SP(?)}"
                            );

                    procedimiento.setLong(
                            1,
                            id
                    );

                    return procedimiento;
                }
        );
    }

}