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
    public DiaryDto.InitializeDiaryResponse initializeDiary() {
        User loginUser = authService.getLoginUser();

        Optional<Diary> optionalDiary = diaryRepository.findDiaryByDiaryStatusAndUser((byte) 0, loginUser);

        if (optionalDiary.isEmpty()) {
            Diary savedDiary = diaryRepository.save(Diary.builder()
                    .diaryStatus((byte) 0)
                    .user(loginUser)
                    .build());
            return DiaryDto.InitializeDiaryResponse.builder()
                    .diaryId(savedDiary.getDiaryId())
                    .build();
        } else {
            return DiaryDto.InitializeDiaryResponse.builder()
                    .diaryId(optionalDiary.get().getDiaryId())
                    .build();
        }
    }

    @Override
    public DiaryDto.CreateDiaryImageResponse createDiaryImage(UUID diaryId, MultipartFile multipartFile) throws IOException {
        User loginUser = authService.getLoginUser();

        Diary diary = verifiedDiary(diaryId);

        DiaryImage savedDiaryImage = diaryImageRepository.save(DiaryImage.builder()
                .image(diaryRepository.getReferenceById(diaryId))
                .build());

        verifyDiaryOwner(diary, loginUser);

        String imageUrl = objectStorageService.uploadDiaryImage(diary.getDiaryId(), savedDiaryImage.getImageId(), multipartFile);

        savedDiaryImage.updateImageUrl(imageUrl);

        return DiaryDto.CreateDiaryImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public Diary verifiedDiary(UUID diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);
        return optionalDiary.orElseThrow();
    }

    public void verifyDiaryOwner(Diary diary, User user) {
        if (!(diary.getUser() == user)) {
            log.info("다이어리 수정 권한이 없습니다.");
        }
    }


}
