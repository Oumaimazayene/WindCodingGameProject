package com.demo.demo.Service;

import com.demo.demo.entity.*;
import org.springframework.web.multipart.MultipartFile;
import com.demo.demo.dtos.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TestSectionService {

        List<TestSectionDto> getAllTestSection();
        void  deleteAllTestSection();

    List<Question> getPrivateQuestionsByTestSectionIdAll(Long testSectionId);
    List<Question_Logique> getPrivateQuestionsByTestSectionId(Long testSectionId, String difficulty, int size);

}


