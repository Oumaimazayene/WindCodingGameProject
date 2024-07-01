package com.demo.demo.mappers;

import com.demo.demo.dtos.TestSection_TechDTo;
import com.demo.demo.entity.Test_Section_Tech;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface TestSectionTechMapper {
    Test_Section_Tech TEST_SECTION_TECH(TestSection_TechDTo testSectionTechDTo);
    TestSection_TechDTo TEST_SECTION_TECH_D_TO(Test_Section_Tech testSectionTech);

    void updateTestSectionTechFromDTo(TestSection_TechDTo testSectionTechDTo,@MappingTarget Test_Section_Tech existingTestSectionTech);
}
