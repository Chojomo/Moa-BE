package com.moa.domain.diary.diary.service;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.global.dto.MultiResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface DiaryService {

    DiaryDto.InitializeDiaryResponse initializeDiary();

    DiaryDto.CreateDiaryImageResponse createDiaryImage(UUID diaryId, MultipartFile multipartFile) throws IOException;

    void updateDiary(DiaryDto.UpdateDiaryRequest req);

    DiaryDto.GetDiaryResponse getDiaryDetails(UUID diaryId);

    void publishDiary(DiaryDto.PublishDiaryRequest req);

    MultiResponseDto<?> getDiaryList(Integer pageNumber, Integer pageSize, String SortType);

    void toggleLikeOnDiary(UUID diaryId);

    DiaryLikeDto.GetDiaryLikesResponse getDiaryLikes(UUID diaryId);

    DiaryDto.UploadThumbnailResponse uploadThumbnail(UUID diaryId, MultipartFile multipartFile) throws IOException;

    Diary findDiaryOrThrow(UUID diaryId);

    void deleteDiary(UUID diaryId);

    Page<UserDiaryDto> getUserDiaryList(UUID userId, Integer pageNumber, Integer pageSize);

    Page<UserLikedDiaryDto> getUserLikedDiaries(UUID userId, Integer pageNumber, Integer pageSize);

}
