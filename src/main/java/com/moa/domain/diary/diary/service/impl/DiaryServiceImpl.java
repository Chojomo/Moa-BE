package com.moa.domain.diary.diary.service.impl;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diary.service.DiaryService;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.repository.DiaryCommentRepository;
import com.moa.domain.diary.diarycommentlike.repository.DiaryCommentLikeRepository;
import com.moa.domain.diary.diaryimage.entity.DiaryImage;
import com.moa.domain.diary.diary.mapper.DiaryMapper;
import com.moa.domain.diary.diaryimage.repository.DiaryImageRepository;
import com.moa.domain.diary.diary.repository.DiaryRepository;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.domain.diary.diarylike.service.DiaryLikeService;
import com.moa.domain.member.entity.User;
import com.moa.global.security.service.AuthService;
import com.moa.global.storage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final DiaryLikeService diaryLikeService;
    private final DiaryCommentRepository diaryCommentRepository;
    private final DiaryCommentLikeRepository diaryCommentLikeRepository;

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

        boolean isDiaryExists = diaryImageRepository.existsByDiaryId(diaryId);

        DiaryImage savedDiaryImage = diaryImageRepository.save(DiaryImage.builder()
                .image(diaryRepository.getReferenceById(diaryId))
                .build());

        checkDiaryOwnership(diary, loginUser);

        String imageUrl = objectStorageService.uploadDiaryImage(diary.getDiaryId(), savedDiaryImage.getImageId(), multipartFile);

        if (!isDiaryExists) {
            diary.updateDiaryThumbnail(imageUrl);
        }

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

        diary.updateDiary(req.getDiaryTitle(), req.getDiaryContents(), req.getIsDiaryPublic(), diary.getDiaryStatus());
    }

    @Override
    public DiaryDto.GetDiaryResponse getDiaryDetails(UUID diaryId) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryWithUserOrThrow(diaryId);

        if (!diary.getIsDairyPublic() && !loginUser.getUserId().equals(diary.getUser().getUserId())) {
            throw new RuntimeException();
        }

        diary.incrementViewCount();

        diaryRepository.save(diary);

        Boolean diaryLiked = false;

        if (loginUser != null) {
            diaryLiked = diaryLikeService.isDiaryLiked(diary, loginUser);
        }

        List<DiaryComment> commentsAndReplies = diaryCommentRepository.findCommentsByDiaryId(diaryId);

        Map<UUID, Boolean> loginUserCommentLikeData = getLoginUserCommentLikeData(loginUser, commentsAndReplies);

        return diaryMapper.diaryToGetDiaryResponse(loginUser, diary, commentsAndReplies, diaryLiked, loginUserCommentLikeData);
    }

    @Override
    public void publishDiary(DiaryDto.PublishDiaryRequest req) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(req.getDiaryId());

        checkDiaryOwnership(diary, loginUser);

        diary.publishDiary(req.getDiaryTitle(), req.getDiaryContents(), req.getDiaryThumbnail(), req.getIsDiaryPublic());
    }

    @Override
    public Page<DiaryDto.DiaryPreview> getDiaryList(Pageable pageable) {
        return diaryRepository.findAllWithUser(pageable)
                .map(diaryMapper::diaryTodiaryPreview);
    }

    @Override
    public void toggleLikeOnDiary(UUID diaryId) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(diaryId);

        boolean isLiked = diaryLikeService.toggleLikeOnDiary(loginUser, diary);

        if (isLiked) {
            diary.incrementLikeCount();
        } else {
            diary.decrementLikeCount();
        }

        diaryRepository.save(diary);
    }

    @Override
    public DiaryLikeDto.GetDiaryLikesResponse getDiaryLikes(UUID diaryId) {
        Diary diary = findDiaryOrThrow(diaryId);

        return diaryLikeService.getDiaryLikes(diary);
    }

    @Override
    public DiaryDto.UploadThumbnailResponse uploadThumbnail(UUID diaryId, MultipartFile multipartFile) throws IOException {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(diaryId);

        checkDiaryOwnership(diary, loginUser);

        DiaryImage savedDiaryImage = diaryImageRepository.save(DiaryImage.builder()
                .image(diaryRepository.getReferenceById(diaryId))
                .build());

        String thumbnailUrl = objectStorageService.uploadDiaryImage(diary.getDiaryId(), savedDiaryImage.getImageId(), multipartFile);

        savedDiaryImage.updateImageUrl(thumbnailUrl);

        diary.updateDiaryThumbnail(thumbnailUrl);

        return DiaryDto.UploadThumbnailResponse.builder()
                .thumbnailUrl(thumbnailUrl)
                .build();
    }

    @Override
    public void deleteDiary(UUID diaryId) {
        User loginUser = authService.getLoginUser();

        Diary diary = findDiaryOrThrow(diaryId);

        checkDiaryOwnership(diary, loginUser);

        diary.deleteDiary();
    }

    @Override
    public Page<UserDiaryDto> getUserDiaryList(UUID userId, Pageable pageable) {
        return diaryRepository.findUserDiaryList(userId, pageable);
    }

    @Override
    public Page<UserLikedDiaryDto> getUserLikedDiaries(UUID userId, Pageable pageable) {
        return diaryRepository.findUserLikedDiaryList(userId, pageable);
    }

    @Override
    public Diary findDiaryOrThrow(UUID diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findNotDeletedDiaryById(diaryId);
        return optionalDiary.orElseThrow();
    }

    public Diary findDiaryWithUserOrThrow(UUID diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findDiaryWithUserById(diaryId);
        return optionalDiary.orElseThrow();
    }

    public void checkDiaryOwnership(Diary diary, User user) {
        if (!diary.getUser().getUserId().equals(user.getUserId())) {
            log.info("다이어리 수정 권한이 없습니다.");
        }
    }

    private Map<UUID, Boolean> getLoginUserCommentLikeData(User loginUser, List<DiaryComment> commentsAndReplies) {
        if (loginUser == null) {
            return Map.of();
        }

        List<UUID> commentIds = extractAllCommentIdsWithStream(commentsAndReplies);

        List<UUID> diaryCommentLikesByUser = diaryCommentLikeRepository.findDiaryCommentLikesByUser(loginUser, commentIds);

        return diaryCommentLikesByUser.stream()
                .collect(Collectors.toMap(id -> id, id -> true));
    }

    public List<UUID> extractAllCommentIdsWithStream(List<DiaryComment> comments) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        return comments.stream()
                .flatMap(comment -> Stream.concat(
                        Stream.of(comment.getDiaryCommentId()),
                        extractAllCommentIdsWithStream(comment.getChildrenComments()).stream()
                ))
                .toList();
    }

}
