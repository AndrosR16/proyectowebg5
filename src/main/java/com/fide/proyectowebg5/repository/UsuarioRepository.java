package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fide.proyectowebg5.model.Usuario;

import oracle.jdbc.OracleTypes;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Usuario> listar() {

        return jdbcTemplate.execute(
                (Connection connection) -> {

                    CallableStatement procedimiento =
                            connection.prepareCall(
                                    "{call FIDE_PROYECTO_PCK.FIDE_USUARIO_LISTAR_SP(?)}"
                            );

                    procedimiento.registerOutParameter(
                            1,
                            OracleTypes.CURSOR
                    );

                    return procedimiento;
                },
                (CallableStatement procedimiento) -> {

                    List<Usuario> usuarios = new ArrayList<>();

                    procedimiento.execute();

                    try (ResultSet resultado =
                                 (ResultSet) procedimiento.getObject(1)) {

                        while (resultado.next()) {

                            Usuario usuario = new Usuario();

                            usuario.setIdUsuario(
                                    resultado.getLong("ID_USUARIO")
                            );

                            usuario.setNombre(
                                    resultado.getString("NOMBRE")
                            );

                            usuario.setApellidoP(
                                    resultado.getString("APELLIDO_P")
                            );

                            usuario.setApellidoM(
                                    resultado.getString("APELLIDO_M")
                            );

                            usuario.setRol(
                                    resultado.getString("ROL")
                            );

                            usuario.setIdEstado(
                                    resultado.getLong("ID_ESTADO")
                            );

                            usuario.setNombreEstado(
                                    resultado.getString("NOMBRE_ESTADO")
                            );

                            usuarios.add(usuario);
                        }
                    }

                    return usuarios;
                }
        );
    }

    public Usuario buscarPorId(Long id) {

        return listar()
                .stream()
                .filter(usuario -> usuario.getIdUsuario().equals(id))
                .findFirst()
                .orElse(null);
    }
}