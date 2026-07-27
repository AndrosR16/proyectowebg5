package com.fide.proyectowebg5.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
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

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_USUARIO_LISTAR_SP(?)}");

                                        procedimiento.registerOutParameter(
                                                        1,
                                                        OracleTypes.CURSOR);

                                        return procedimiento;
                                },
                                (CallableStatement procedimiento) -> {

                                        List<Usuario> usuarios = new ArrayList<>();

                                        procedimiento.execute();

                                        try (ResultSet resultado = (ResultSet) procedimiento.getObject(1)) {

                                                while (resultado.next()) {

                                                        Usuario usuario = new Usuario();

                                                        usuario.setIdUsuario(
                                                                        resultado.getLong("ID_USUARIO"));

                                                        usuario.setNombre(
                                                                        resultado.getString("NOMBRE"));

                                                        usuario.setApellidoP(
                                                                        resultado.getString("APELLIDO_P"));

                                                        usuario.setApellidoM(
                                                                        resultado.getString("APELLIDO_M"));

                                                        usuario.setUsername(
                                                                        resultado.getString("USERNAME"));

                                                        usuario.setRol(
                                                                        resultado.getString("ROL"));

                                                        usuario.setIdEstado(
                                                                        resultado.getLong("ID_ESTADO"));

                                                        usuario.setNombreEstado(
                                                                        resultado.getString("NOMBRE_ESTADO"));

                                                        usuarios.add(usuario);
                                                }
                                        }

                                        return usuarios;
                                });
        }

        public Usuario buscarPorId(Long id) {

                return listar()
                                .stream()
                                .filter(usuario -> usuario.getIdUsuario().equals(id))
                                .findFirst()
                                .orElse(null);
        }

        public void guardar(Usuario usuario) {

                jdbcTemplate.update(
                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_USUARIO_INSERT_SP(?, ?, ?, ?, ?, ?, ?, ?)}");

                                        procedimiento.setNull(
                                                        1,
                                                        Types.NUMERIC);

                                        procedimiento.setString(
                                                        2,
                                                        usuario.getNombre());

                                        procedimiento.setString(
                                                        3,
                                                        usuario.getApellidoP());

                                        if (usuario.getApellidoM() != null
                                                        && !usuario.getApellidoM().isBlank()) {

                                                procedimiento.setString(
                                                                4,
                                                                usuario.getApellidoM());

                                        } else {

                                                procedimiento.setNull(
                                                                4,
                                                                Types.VARCHAR);
                                        }

                                        procedimiento.setString(
                                                        5,
                                                        usuario.getUsername());

                                        procedimiento.setString(
                                                        6,
                                                        usuario.getContrasena());

                                        procedimiento.setString(
                                                        7,
                                                        usuario.getRol());

                                        procedimiento.setLong(
                                                        8,
                                                        usuario.getIdEstado());

                                        return procedimiento;
                                });
        }

        public void actualizar(Usuario usuario) {

                jdbcTemplate.update(
                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_USUARIO_UPDATE_SP(?, ?, ?, ?, ?, ?, ?, ?)}");

                                        procedimiento.setLong(
                                                        1,
                                                        usuario.getIdUsuario());

                                        procedimiento.setString(
                                                        2,
                                                        usuario.getNombre());

                                        procedimiento.setString(
                                                        3,
                                                        usuario.getApellidoP());

                                        if (usuario.getApellidoM() != null
                                                        && !usuario.getApellidoM().isBlank()) {

                                                procedimiento.setString(
                                                                4,
                                                                usuario.getApellidoM());

                                        } else {

                                                procedimiento.setNull(
                                                                4,
                                                                Types.VARCHAR);
                                        }

                                        procedimiento.setString(
                                                        5,
                                                        usuario.getUsername());

                                        if (usuario.getContrasena() != null
                                                        && !usuario.getContrasena().isBlank()) {

                                                procedimiento.setString(
                                                                6,
                                                                usuario.getContrasena());

                                        } else {

                                                procedimiento.setNull(
                                                                6,
                                                                Types.VARCHAR);
                                        }

                                        procedimiento.setString(
                                                        7,
                                                        usuario.getRol());

                                        procedimiento.setLong(
                                                        8,
                                                        usuario.getIdEstado());

                                        return procedimiento;
                                });
        }

        public void inactivar(Long idUsuario) {

                jdbcTemplate.update(
                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_USUARIO_DELETE_SP(?, ?)}");

                                        procedimiento.setLong(
                                                        1,
                                                        idUsuario);

                                        procedimiento.setLong(
                                                        2,
                                                        2L);

                                        return procedimiento;
                                });
        }

        public Usuario login(String username, String contrasena) {

                return jdbcTemplate.execute(

                                (Connection connection) -> {

                                        CallableStatement procedimiento = connection.prepareCall(
                                                        "{call FIDE_PROYECTO_PCK.FIDE_VALIDAR_LOGIN_SP(?, ?, ?)}");

                                        procedimiento.setString(1, username);
                                        procedimiento.setString(2, contrasena);
                                        procedimiento.registerOutParameter(3, OracleTypes.CURSOR);

                                        return procedimiento;
                                },

                                (CallableStatement procedimiento) -> {

                                        procedimiento.execute();

                                        try (ResultSet resultado = (ResultSet) procedimiento.getObject(3)) {

                                                if (resultado.next()) {

                                                        Usuario usuario = new Usuario();

                                                        usuario.setIdUsuario(
                                                                        resultado.getLong("ID_USUARIO"));

                                                        usuario.setUsername(
                                                                        resultado.getString("USERNAME"));

                                                        usuario.setNombre(
                                                                        resultado.getString("NOMBRE"));

                                                        usuario.setApellidoP(
                                                                        resultado.getString("APELLIDO_P"));

                                                        usuario.setApellidoM(
                                                                        resultado.getString("APELLIDO_M"));

                                                        usuario.setRol(
                                                                        resultado.getString("ROL"));

                                                        usuario.setIdEstado(
                                                                        resultado.getLong("ID_ESTADO"));

                                                        return usuario;
                                                }

                                                return null;
                                        }

                                }

                );

        }

}