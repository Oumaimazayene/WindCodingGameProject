package com.demo.demo.mappers;

import com.demo.demo.dtos.TestSectionDto;
import com.demo.demo.entity.Test_Section;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

    @Mapper(componentModel = "spring")
    public interface TestSectionMapper {
        Test_Section TEST_SECTION(TestSectionDto testSectionDto);
        TestSectionDto TEST_SECTION_DTO(Test_Section testSection);

        void updateTestSectionFromDTo(TestSectionDto testSectionDto,@MappingTarget Test_Section existingTestSection);



}
