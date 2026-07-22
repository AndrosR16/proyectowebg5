package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.MetodoPago;

import oracle.jdbc.OracleTypes;

@Repository
public class MetodoPagoRepository {

    private final JdbcTemplate jdbcTemplate;

    public MetodoPagoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MetodoPago> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_METODO_PAGO_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<MetodoPago> metodosPago = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            MetodoPago metodoPago = new MetodoPago();

                            metodoPago.setIdMetodoPago(
                                    resultado.getLong("ID_METODO_PAGO")
                            );

                            metodoPago.setNombreMetodo(
                                    resultado.getString("NOMBRE_METODO")
                            );

                            metodoPago.setDescripcion(
                                    resultado.getString("DESCRIPCION")
                            );

                            metodoPago.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            metodosPago.add(metodoPago);
                        }
                    }

                    return metodosPago;
                }
        );
    }

    public MetodoPago buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(metodo -> metodo.getIdMetodoPago().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void insertar(MetodoPago metodoPago) {

        jdbcTemplate.update(connection -> {

            CallableStatement procedimiento = connection.prepareCall(
                    "{call FIDE_PROYECTO_PCK.FIDE_METODO_PAGO_INSERT_SP(?,?,?,?)}"
            );

            procedimiento.setLong(1, metodoPago.getIdMetodoPago());
            procedimiento.setString(2, metodoPago.getNombreMetodo());
            procedimiento.setString(3, metodoPago.getDescripcion());
            procedimiento.setLong(4, metodoPago.getIdEstado());

            return procedimiento;
        });
    }

    public void actualizar(MetodoPago metodoPago) {

        jdbcTemplate.update(connection -> {

            CallableStatement procedimiento = connection.prepareCall(
                    "{call FIDE_PROYECTO_PCK.FIDE_METODO_PAGO_UPDATE_SP(?,?,?,?)}"
            );

            procedimiento.setLong(1, metodoPago.getIdMetodoPago());
            procedimiento.setString(2, metodoPago.getNombreMetodo());
            procedimiento.setString(3, metodoPago.getDescripcion());
            procedimiento.setLong(4, metodoPago.getIdEstado());

            return procedimiento;
        });
    }

    public void eliminar(Long id) {

        jdbcTemplate.update(connection -> {

            CallableStatement procedimiento = connection.prepareCall(
                    "{call FIDE_PROYECTO_PCK.FIDE_METODO_PAGO_DELETE_SP(?)}"
            );

            procedimiento.setLong(1, id);

            return procedimiento;
        });
    }
}