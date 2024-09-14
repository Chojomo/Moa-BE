package com.moa.domain.diary.service.impl;

import com.moa.domain.diary.dto.DiaryDto;
import com.moa.domain.diary.entity.Diary;
import com.moa.domain.diary.entity.DiaryImage;
import com.moa.domain.diary.mapper.DiaryMapper;
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
    private final DiaryMapper diaryMapper;

    @Override
    public DiaryDto.InitializeDiaryResponse initializeDiary() {
        User loginUser = authService.getLoginUser();

        Optional<Diary> optionalDiary = diaryRepository.findDiaryByDiaryStatusAndUser((byte) 0, loginUser);

        optionalDiary.ifPresent(diaryRepository::delete);

        Diary savedDiary = diaryRepository.save(Diary.builder()
                .diaryStatus((byte) 0)
                .user(loginUser)
                .build());

        return DiaryDto.InitializeDiaryResponse.builder()
                .diaryId(savedDiary.getDiaryId())
                .build();
    }

    @Override
    public DiaryDto.CreateDiaryImageResponse createDiaryImage(UUID diaryId, MultipartFile multipartFile) throws IOException {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(diaryId);

        DiaryImage savedDiaryImage = diaryImageRepository.save(DiaryImage.builder()
                .image(diaryRepository.getReferenceById(diaryId))
                .build());

        checkDiaryOwnership(diary, loginUser);

        String imageUrl = objectStorageService.uploadDiaryImage(diary.getDiaryId(), savedDiaryImage.getImageId(), multipartFile);

        savedDiaryImage.updateImageUrl(imageUrl);

        return DiaryDto.CreateDiaryImageResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }

    @Override
    public void updateDiary(DiaryDto.UpdateDiaryRequest req) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(req.getDiaryId());

        checkDiaryOwnership(diary, loginUser);

        diary.updateDiary(req.getDiaryTitle(), req.getDiaryContents(), req.getThumbnail(), req.getIsDiaryPublic(), diary.getDiaryStatus());
    }

    @Override
    public DiaryDto.GetDiaryResponse getDiaryDetails(UUID diaryId) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(diaryId);

        if (!diary.getIsDairyPublic() && !loginUser.getUserId().equals(diary.getUser().getUserId())) {
            throw new RuntimeException();
        }

        return diaryMapper.diaryToGetDiaryResponse(diary);
    }

    @Override
    public void publishDiary(DiaryDto.PublishDiaryRequest req) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(req.getDiaryId());

        checkDiaryOwnership(diary, loginUser);

        diary.publishDiary(req.getDiaryTitle(), req.getDiaryContents(), req.getDiaryThumbnail(), req.getIsDiaryPublic());
    }

    public Diary findDiaryOrThrow(UUID diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);
        return optionalDiary.orElseThrow();
    }

    public void checkDiaryOwnership(Diary diary, User user) {
        if (!diary.getUser().getUserId().equals(user.getUserId())) {
            log.info("다이어리 수정 권한이 없습니다.");
        }
    }

}
