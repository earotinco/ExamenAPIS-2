package com.codigo.ms_empresa.mapper;

import com.codigo.ms_empresa.aggregates.dto.EmpresaDto;
import com.codigo.ms_empresa.aggregates.response.ResponseSunat;
import com.codigo.ms_empresa.entity.EmpresaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {


    private final ModelMapper modelMapper;

    public EmpresaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmpresaDto toDto(EmpresaEntity entity) {
        return modelMapper.map(entity, EmpresaDto.class);
    }

    public EmpresaEntity toEntity(EmpresaDto dto) {
        return modelMapper.map(dto, EmpresaEntity.class);
    }

    public EmpresaDto toDto2(ResponseSunat responseSunat){
        return modelMapper.map(responseSunat, EmpresaDto.class);
    }

    public ResponseSunat toResponse(EmpresaDto dto){
        return modelMapper.map(dto, ResponseSunat.class);
    }





}
