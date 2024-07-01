package com.demo.demo.mappers;

import com.demo.demo.dtos.TestSectionDto;
import com.demo.demo.dtos.TestSection_LogiqueDTo;
import com.demo.demo.entity.Test_Section;
import com.demo.demo.entity.Test_Section_Logique;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TestSectionLogiqueMapper {
    TestSection_LogiqueDTo TEST_SECTION_LOGIQUE_D_TO(Test_Section_Logique testSectionLogique);
    Test_Section_Logique TEST_SECTION_LOGIQUE(TestSection_LogiqueDTo testSectionLogiqueDTo);

    void updateTestSectionLogFromDTo(TestSection_LogiqueDTo testSectionLogiqueDTo, @MappingTarget Test_Section_Logique existingTestSectionLog);

}
