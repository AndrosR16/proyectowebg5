package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Posicion;

import oracle.jdbc.OracleTypes;

@Repository
public class PosicionRepository {

    private final JdbcTemplate jdbcTemplate;


    public PosicionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Posicion> listar() {

        return jdbcTemplate.execute(

            (Connection connection) -> {

                CallableStatement procedimiento =
                        connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_POSICION_LISTAR_SP(?)}"
                        );

                procedimiento.registerOutParameter(
                        1,
                        OracleTypes.CURSOR
                );

                return procedimiento;
            },


            (CallableStatement procedimiento) -> {

                List<Posicion> posiciones = new ArrayList<>();

                procedimiento.execute();


                try (ResultSet resultado =
                        (ResultSet) procedimiento.getObject(1)) {


                    while (resultado.next()) {


                        Posicion posicion = new Posicion();


                        posicion.setIdPosicion(
                                resultado.getLong("ID_POSICION")
                        );


                        posicion.setDescripcion(
                                resultado.getString("DESCRIPCION")
                        );


                        posicion.setIdEstado(
                                resultado.getLong("ID_ESTADO")
                        );


                        posiciones.add(posicion);

                    }

                }


                return posiciones;

            });
    }



    public Posicion buscarPorId(Long idPosicion) {


        return jdbcTemplate.execute(

            (Connection connection) -> {


                CallableStatement procedimiento =
                        connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_POSICION_BUSCAR_SP(?,?)}"
                        );


                procedimiento.setLong(
                        1,
                        idPosicion
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


                        Posicion posicion = new Posicion();


                        posicion.setIdPosicion(
                                resultado.getLong("ID_POSICION")
                        );


                        posicion.setDescripcion(
                                resultado.getString("DESCRIPCION")
                        );


                        posicion.setIdEstado(
                                resultado.getLong("ID_ESTADO")
                        );


                        return posicion;

                    }

                }


                return null;

            });
    }



    public void insertar(Posicion posicion) {


        jdbcTemplate.update(

            connection -> {


                CallableStatement procedimiento =
                        connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_POSICION_INSERT_SP(?,?,?)}"
                        );


                procedimiento.setLong(
                        1,
                        posicion.getIdPosicion()
                );


                procedimiento.setString(
                        2,
                        posicion.getDescripcion()
                );


                procedimiento.setLong(
                        3,
                        posicion.getIdEstado()
                );


                return procedimiento;

            });
    }



    public void actualizar(Posicion posicion) {


        jdbcTemplate.update(

            connection -> {


                CallableStatement procedimiento =
                        connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_POSICION_UPDATE_SP(?,?,?)}"
                        );


                procedimiento.setLong(
                        1,
                        posicion.getIdPosicion()
                );


                procedimiento.setString(
                        2,
                        posicion.getDescripcion()
                );


                procedimiento.setLong(
                        3,
                        posicion.getIdEstado()
                );


                return procedimiento;

            });
    }



    public void eliminar(Long idPosicion) {


        jdbcTemplate.update(

            connection -> {


                CallableStatement procedimiento =
                        connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_POSICION_DELETE_SP(?)}"
                        );


                procedimiento.setLong(
                        1,
                        idPosicion
                );


                return procedimiento;

            });
    }

}