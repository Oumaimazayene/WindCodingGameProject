package com.demo.demo.mappers;

import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TypeMapper {

TypeDto toTypeDTo(Type type);
Type ToType(TypeDto typeDto);

    void updateTypeFromDTO(TypeDto typeDTO, @MappingTarget Type existingType);}
