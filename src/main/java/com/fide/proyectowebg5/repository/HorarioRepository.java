package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Horario;

import oracle.jdbc.OracleTypes;

@Repository
public class HorarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public HorarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Horario> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_HORARIO_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Horario> horarios = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Horario horario = new Horario();

                            horario.setIdHorario(
                                    resultado.getLong("ID_HORARIO")
                            );

                            horario.setIdCancha(
                                    resultado.getLong("ID_CANCHA")
                            );

                            horario.setCancha(
                                    resultado.getString("NOMBRE_CANCHA")
                            );

                            horario.setDiaSemana(
                                    resultado.getString("DIA_SEMANA")
                            );

                            horario.setHoraInicio(
                                    LocalTime.parse(resultado.getString("HORA_INICIO"))
                            );

                            horario.setHoraFin(
                                    LocalTime.parse(resultado.getString("HORA_FIN"))
                            );

                            horario.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            horario.setEstado(
                                    resultado.getString("ESTADO")
                            );

                            horarios.add(horario);
                        }
                    }

                    return horarios;
                }
        );
    }

    public Horario buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(h -> h.getIdHorario().equals(id))
                .findFirst()
                .orElse(null);

    }

    public void insertar(Horario horario) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_HORARIO_INSERT_SP(?,?,?,?,?,?)}"
                    );

                    procedimiento.setLong(1, horario.getIdHorario());
                    procedimiento.setLong(2, horario.getIdCancha());
                    procedimiento.setString(3, horario.getDiaSemana());
                    procedimiento.setTime(4, java.sql.Time.valueOf(horario.getHoraInicio()));
                    procedimiento.setTime(5, java.sql.Time.valueOf(horario.getHoraFin()));
                    procedimiento.setLong(6, horario.getIdEstado());

                    return procedimiento;
                }
        );
    }

    public void actualizar(Horario horario) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_HORARIO_UPDATE_SP(?,?,?,?,?,?)}"
                    );

                    procedimiento.setLong(1, horario.getIdHorario());
                    procedimiento.setLong(2, horario.getIdCancha());
                    procedimiento.setString(3, horario.getDiaSemana());
                    procedimiento.setTime(4, java.sql.Time.valueOf(horario.getHoraInicio()));
                    procedimiento.setTime(5, java.sql.Time.valueOf(horario.getHoraFin()));
                    procedimiento.setLong(6, horario.getIdEstado());

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long id) {

        jdbcTemplate.update(
                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_HORARIO_DELETE_SP(?)}"
                    );

                    procedimiento.setLong(1, id);

                    return procedimiento;
                }
        );
    }

}