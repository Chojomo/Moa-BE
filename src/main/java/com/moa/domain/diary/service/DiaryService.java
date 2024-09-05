package com.moa.domain.diary.service;

import com.moa.domain.diary.dto.DiaryDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface DiaryService {

    DiaryDto.InitializeDiaryResponse initializeDiary();

    DiaryDto.CreateDiaryImageResponse createDiaryImage(UUID imageId, MultipartFile multipartFile) throws IOException;

}
