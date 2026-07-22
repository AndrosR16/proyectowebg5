package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.SuperficieCancha;

import oracle.jdbc.OracleTypes;

@Repository
public class SuperficieCanchaRepository {

    private final JdbcTemplate jdbcTemplate;

    public SuperficieCanchaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SuperficieCancha> listar() {

        return jdbcTemplate.execute(

                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_SUPERFICIE_CANCHA_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },

                (CallableStatement procedimiento) -> {

                    List<SuperficieCancha> superficies = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            SuperficieCancha superficie = new SuperficieCancha();

                            superficie.setIdSuperficie(
                                    resultado.getLong("ID_SUPERFICIE")
                            );

                            superficie.setDescripcion(
                                    resultado.getString("DESCRIPCION")
                            );

                            superficie.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            superficies.add(superficie);
                        }
                    }

                    return superficies;
                }
        );
    }

    public SuperficieCancha buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(s -> s.getIdSuperficie().equals(id))
                .findFirst()
                .orElse(null);

    }

    public void insertar(SuperficieCancha superficie) {

        jdbcTemplate.update(

                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_SUPERFICIE_CANCHA_INSERT_SP(?,?,?)}"
                    );

                    procedimiento.setLong(1, superficie.getIdSuperficie());
                    procedimiento.setString(2, superficie.getDescripcion());
                    procedimiento.setLong(3, superficie.getIdEstado());

                    return procedimiento;
                }
        );
    }

    public void actualizar(SuperficieCancha superficie) {

        jdbcTemplate.update(

                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_SUPERFICIE_CANCHA_UPDATE_SP(?,?,?)}"
                    );

                    procedimiento.setLong(1, superficie.getIdSuperficie());
                    procedimiento.setString(2, superficie.getDescripcion());
                    procedimiento.setLong(3, superficie.getIdEstado());

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long id) {

        jdbcTemplate.update(

                connection -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_SUPERFICIE_CANCHA_DELETE_SP(?)}"
                    );

                    procedimiento.setLong(1, id);

                    return procedimiento;
                }
        );
    }

}