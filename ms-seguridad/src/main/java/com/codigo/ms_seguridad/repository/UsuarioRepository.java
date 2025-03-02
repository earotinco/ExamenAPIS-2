package com.codigo.ms_seguridad.repository;

import com.codigo.ms_seguridad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByUssername(String ussername);

    /*JPQL --> usa la relacion many to many*/
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombreRol = :rol")
    List<Usuario> findByRole(@Param("rol") String rol);



}
