package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Factura;
import com.fide.proyectowebg5.model.Pago;

import oracle.jdbc.OracleTypes;

@Repository
public class PagoRepository {

    private final JdbcTemplate jdbcTemplate;

    public PagoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pago> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_PAGO_LISTAR_SP(?)}"
                            );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Pago> pagos = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Pago pago = new Pago();

                            pago.setIdPago(
                                    resultado.getLong("ID_PAGO")
                            );

                            pago.setIdReserva(
                                    resultado.getLong("ID_RESERVA")
                            );

                            pago.setMonto(
                                    resultado.getBigDecimal("MONTO")
                            );

                            pago.setIdMetodoPago(
                                    resultado.getLong("ID_METODO_PAGO")
                            );

                            pago.setNombreMetodo(
                                    resultado.getString("NOMBRE_METODO")
                            );

                            pago.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            pago.setNombreEstado(
                                    resultado.getString("NOMBRE_ESTADO")
                            );

                            pagos.add(pago);
                        }
                    }

                    return pagos;
                }
        );
    }

    public Pago buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(pago -> pago.getIdPago().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void insertar(Pago pago) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_PAGO_INSERT_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            pago.getIdPago()
                    );

                    procedimiento.setLong(
                            2,
                            pago.getIdReserva()
                    );

                    procedimiento.setBigDecimal(
                            3,
                            pago.getMonto()
                    );

                    procedimiento.setLong(
                            4,
                            pago.getIdMetodoPago()
                    );

                    procedimiento.setLong(
                            5,
                            pago.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void insertarConFactura(
            Pago pago,
            Factura factura) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_PAGO_FACTURA_INSERT_SP(?,?,?,?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            pago.getIdPago()
                    );

                    procedimiento.setLong(
                            2,
                            pago.getIdReserva()
                    );

                    procedimiento.setBigDecimal(
                            3,
                            pago.getMonto()
                    );

                    procedimiento.setLong(
                            4,
                            pago.getIdMetodoPago()
                    );

                    procedimiento.setLong(
                            5,
                            pago.getIdEstado()
                    );

                    procedimiento.setLong(
                            6,
                            factura.getIdFactura()
                    );

                    procedimiento.setString(
                            7,
                            factura.getNumeroFactura()
                    );

                    procedimiento.setLong(
                            8,
                            factura.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void actualizar(Pago pago) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_PAGO_UPDATE_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            pago.getIdPago()
                    );

                    procedimiento.setLong(
                            2,
                            pago.getIdReserva()
                    );

                    procedimiento.setBigDecimal(
                            3,
                            pago.getMonto()
                    );

                    procedimiento.setLong(
                            4,
                            pago.getIdMetodoPago()
                    );

                    procedimiento.setLong(
                            5,
                            pago.getIdEstado()
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
                                    "{call FIDE_PROYECTO_PCK.FIDE_PAGO_DELETE_SP(?)}"
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