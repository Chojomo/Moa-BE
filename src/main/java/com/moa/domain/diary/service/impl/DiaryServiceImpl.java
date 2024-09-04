package com.moa.domain.diary.service.impl;

import com.moa.domain.diary.dto.DiaryDto;
import com.moa.domain.diary.entity.Diary;
import com.moa.domain.diary.entity.DiaryImage;
import com.moa.domain.diary.repository.DiaryImageRepository;
import com.moa.domain.diary.repository.DiaryRepository;
import com.moa.domain.diary.service.DiaryService;
import com.moa.domain.member.entity.User;
import com.moa.global.security.service.AuthService;
import com.moa.global.storage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final AuthService authService;
    private final ObjectStorageService objectStorageService;

    @Override
    public void initializeDiary() {
        User loginUser = authService.getLoginUser();

        Optional<Diary> optionalDiary = diaryRepository.findDiaryByDiaryStatusAndUser((byte) 0, loginUser);

        if (optionalDiary.isEmpty()) {
            diaryRepository.save(Diary.builder()
                    .diaryStatus((byte) 0)
                    .user(loginUser)
                    .build());
        }
    }

    @Override
    public DiaryDto.CreateDiaryImageResponse createDiaryImage(UUID imageId, MultipartFile multipartFile) throws IOException {
        User loginUser = authService.getLoginUser();

        DiaryImage savedDiaryImage = diaryImageRepository.save(DiaryImage.builder()
                .image(diaryRepository.getReferenceById(imageId))
                .build());

        String imageUrl = objectStorageService.uploadDiaryImage(loginUser.getUserNickname(), savedDiaryImage.getImageId(), multipartFile);

        savedDiaryImage.updateImageUrl(imageUrl);

        return DiaryDto.CreateDiaryImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }

}
