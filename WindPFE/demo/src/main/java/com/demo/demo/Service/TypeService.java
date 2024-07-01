package com.demo.demo.Service;


import com.demo.demo.dtos.TypeDto;
import com.demo.demo.entity.Type;

import javax.lang.model.util.Types;
import java.util.List;

public interface TypeService {
    Type getTypeById(Long id);
    List<TypeDto> getAllTypes();
    TypeDto createType(TypeDto typeDTO);
    TypeDto updateType(Long id, TypeDto typeDTO);
    void deleteType(Long id);
    void  deleteAllTypes();


}
