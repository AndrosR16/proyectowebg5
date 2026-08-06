package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Jugador;

import oracle.jdbc.OracleTypes;

@Repository
public class JugadorRepository {

        private final JdbcTemplate jdbcTemplate;

        public JugadorRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        public List<Jugador> listar() {

                return jdbcTemplate.execute(
                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_JUGADOR_LISTAR_SP(?)}");

                                        procedimiento.registerOutParameter(
                                                        1,
                                                        OracleTypes.CURSOR);

                                        return procedimiento;
                                },

                                (CallableStatement procedimiento) -> {

                                        List<Jugador> jugadores = new ArrayList<>();

                                        procedimiento.execute();

                                        try (ResultSet resultado = (ResultSet) procedimiento.getObject(1)) {

                                                while (resultado.next()) {

                                                        Jugador jugador = new Jugador();

                                                        jugador.setIdJugador(
                                                                        resultado.getLong("ID_JUGADOR"));

                                                        jugador.setNombre(
                                                                        resultado.getString("NOMBRE"));

                                                        jugador.setApellidoP(
                                                                        resultado.getString("APELLIDO_P"));

                                                        jugador.setApellidoM(
                                                                        resultado.getString("APELLIDO_M"));

                                                        jugador.setCedula(
                                                                        resultado.getString("CEDULA"));

                                                        jugador.setFechaNacimiento(
                                                                        resultado.getDate("FECHA_NACIMIENTO")
                                                                                        .toLocalDate());

                                                        jugador.setDorsal(
                                                                        resultado.getInt("DORSAL"));

                                                        jugador.setPosicion(
                                                                        resultado.getString("POSICION"));

                                                        jugador.setEstado(
                                                                        resultado.getString("ESTADO"));

                                                        jugadores.add(jugador);
                                                }
                                        }

                                        return jugadores;
                                });
        }

        public Jugador buscarPorId(Long idJugador) {

                return jdbcTemplate.execute(

                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_JUGADOR_BUSCAR_SP(?,?)}");

                                        procedimiento.setLong(
                                                        1,
                                                        idJugador);

                                        procedimiento.registerOutParameter(
                                                        2,
                                                        OracleTypes.CURSOR);

                                        return procedimiento;
                                },

                                (CallableStatement procedimiento) -> {

                                        procedimiento.execute();

                                        try (ResultSet resultado = (ResultSet) procedimiento.getObject(2)) {

                                                if (resultado.next()) {

                                                        Jugador jugador = new Jugador();

                                                        jugador.setIdJugador(
                                                                        resultado.getLong("ID_JUGADOR"));

                                                        jugador.setIdPosicion(
                                                                        resultado.getLong("ID_POSICION"));

                                                        jugador.setNombre(
                                                                        resultado.getString("NOMBRE"));

                                                        jugador.setApellidoP(
                                                                        resultado.getString("APELLIDO_P"));

                                                        jugador.setApellidoM(
                                                                        resultado.getString("APELLIDO_M"));

                                                        jugador.setCedula(
                                                                        resultado.getString("CEDULA"));

                                                        jugador.setFechaNacimiento(
                                                                        resultado.getDate("FECHA_NACIMIENTO")
                                                                                        .toLocalDate());

                                                        jugador.setDorsal(
                                                                        resultado.getInt("DORSAL"));

                                                        jugador.setIdEstado(
                                                                        resultado.getLong("ID_ESTADO"));

                                                        return jugador;
                                                }
                                        }

                                        return null;
                                });
        }

        public void insertar(Jugador jugador) {

                jdbcTemplate.update(

                                connection -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_JUGADOR_INSERT_SP(?,?,?,?,?,?,?,?,?)}");

                                        procedimiento.setLong(1, jugador.getIdJugador());
                                        procedimiento.setLong(2, jugador.getIdPosicion());
                                        procedimiento.setString(3, jugador.getNombre());
                                        procedimiento.setString(4, jugador.getApellidoP());
                                        procedimiento.setString(5, jugador.getApellidoM());
                                        procedimiento.setString(6, jugador.getCedula());
                                        procedimiento.setDate(7, Date.valueOf(jugador.getFechaNacimiento()));
                                        procedimiento.setInt(8, jugador.getDorsal());
                                        procedimiento.setLong(9, jugador.getIdEstado());

                                        return procedimiento;
                                });
        }

        public void actualizar(Jugador jugador) {

                jdbcTemplate.update(

                                connection -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_JUGADOR_UPDATE_SP(?,?,?,?,?,?,?,?,?)}");

                                        procedimiento.setLong(1, jugador.getIdJugador());
                                        procedimiento.setLong(2, jugador.getIdPosicion());
                                        procedimiento.setString(3, jugador.getNombre());
                                        procedimiento.setString(4, jugador.getApellidoP());
                                        procedimiento.setString(5, jugador.getApellidoM());
                                        procedimiento.setString(6, jugador.getCedula());
                                        procedimiento.setDate(7, Date.valueOf(jugador.getFechaNacimiento()));
                                        procedimiento.setInt(8, jugador.getDorsal());
                                        procedimiento.setLong(9, jugador.getIdEstado());

                                        return procedimiento;
                                });
        }

        public void eliminar(Long idJugador, Long idEstado) {

                jdbcTemplate.update(

                                connection -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_JUGADOR_DELETE_SP(?,?)}");

                                        procedimiento.setLong(1, idJugador);
                                        procedimiento.setLong(2, idEstado);

                                        return procedimiento;
                                });
        }

}