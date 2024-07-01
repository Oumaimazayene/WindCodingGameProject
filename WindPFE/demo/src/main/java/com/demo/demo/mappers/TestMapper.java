package com.demo.demo.mappers;

import com.demo.demo.dtos.TestDTo;
import com.demo.demo.entity.Test;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TestMapper {

    Test ToTest(TestDTo testDTo);

    TestDTo ToTestDto(Test test);

    void updateTestFromDTO(TestDTo testDTo, @MappingTarget Test existingTest);
}
