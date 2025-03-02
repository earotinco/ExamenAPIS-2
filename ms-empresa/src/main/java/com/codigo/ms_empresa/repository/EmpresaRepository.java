package com.codigo.ms_empresa.repository;

import com.codigo.ms_empresa.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository <EmpresaEntity, Long> {

    Optional<EmpresaEntity> findByNumeroDocumento(String numeroDocumento);
}
