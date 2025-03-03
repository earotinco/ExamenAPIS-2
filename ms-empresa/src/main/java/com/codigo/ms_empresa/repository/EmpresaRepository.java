package com.codigo.ms_empresa.repository;

import com.codigo.ms_empresa.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository <EmpresaEntity, Long> {

    EmpresaEntity findByNumeroDocumento(String numeroDocumento);
}
