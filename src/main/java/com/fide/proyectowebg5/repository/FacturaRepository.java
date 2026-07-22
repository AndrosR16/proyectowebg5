package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Factura;

import oracle.jdbc.OracleTypes;

@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbcTemplate;

    public FacturaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Factura> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento = connection.prepareCall(
                            "{call FIDE_PROYECTO_PCK.FIDE_FACTURA_LISTAR_SP(?)}"
                    );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    procedimiento.execute();

                    ResultSet resultado =
                            (ResultSet) procedimiento.getObject(1);

                    List<Factura> facturas = new ArrayList<>();

                    while (resultado.next()) {

                        Factura factura = new Factura();

                        factura.setIdFactura(
                                resultado.getLong("ID_FACTURA")
                        );

                        factura.setIdPago(
                                resultado.getLong("ID_PAGO")
                        );

                        factura.setNumeroFactura(
                                resultado.getString("NUMERO_FACTURA")
                        );

                        String fechaFactura =
                                resultado.getString("FECHA_FACTURA");

                        if (fechaFactura != null) {
                            factura.setFechaFactura(
                                    java.time.LocalDate.parse(fechaFactura)
                            );
                        }

                        factura.setIdEstado(
                                resultado.getLong("ID_ESTADO")
                        );

                        factura.setIdReserva(
                                resultado.getLong("ID_RESERVA")
                        );

                        factura.setMonto(
                                resultado.getBigDecimal("MONTO")
                        );

                        factura.setNombreMetodo(
                                resultado.getString("NOMBRE_METODO")
                        );

                        factura.setNombreCliente(
                                resultado.getString("NOMBRE_CLIENTE")
                        );

                        factura.setNombreCancha(
                                resultado.getString("NOMBRE_CANCHA")
                        );

                        factura.setDiaSemana(
                                resultado.getString("DIA_SEMANA")
                        );

                        factura.setHoraInicio(
                                resultado.getString("HORA_INICIO")
                        );

                        factura.setHoraFin(
                                resultado.getString("HORA_FIN")
                        );

                        String fechaReserva =
                                resultado.getString("FECHA_RESERVA");

                        if (fechaReserva != null) {
                            factura.setFechaReserva(
                                    java.time.LocalDate.parse(fechaReserva)
                            );
                        }

                        facturas.add(factura);
                    }

                    resultado.close();

                    return facturas;
                }
        );
    }

    public Factura buscarPorId(Long idFactura) {

        return listar()
                .stream()
                .filter(factura ->
                        factura.getIdFactura().equals(idFactura))
                .findFirst()
                .orElse(null);
    }

    public void insertar(Factura factura) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_FACTURA_INSERT_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            factura.getIdFactura()
                    );

                    procedimiento.setLong(
                            2,
                            factura.getIdPago()
                    );

                    procedimiento.setString(
                            3,
                            factura.getNumeroFactura()
                    );

                    procedimiento.setDate(
                            4,
                            java.sql.Date.valueOf(
                                    factura.getFechaFactura()
                            )
                    );

                    procedimiento.setLong(
                            5,
                            factura.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void actualizar(Factura factura) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_FACTURA_UPDATE_SP(?,?,?,?,?)}"
                            );

                    procedimiento.setLong(
                            1,
                            factura.getIdFactura()
                    );

                    procedimiento.setLong(
                            2,
                            factura.getIdPago()
                    );

                    procedimiento.setString(
                            3,
                            factura.getNumeroFactura()
                    );

                    procedimiento.setDate(
                            4,
                            java.sql.Date.valueOf(
                                    factura.getFechaFactura()
                            )
                    );

                    procedimiento.setLong(
                            5,
                            factura.getIdEstado()
                    );

                    return procedimiento;
                }
        );
    }

    public void eliminar(Long idFactura) {

        jdbcTemplate.update(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_FACTURA_DELETE_SP(?)}"
                            );

                    procedimiento.setLong(
                            1,
                            idFactura
                    );

                    return procedimiento;
                }
        );
    }
}