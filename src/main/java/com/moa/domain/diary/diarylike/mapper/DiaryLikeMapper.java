package com.moa.domain.diary.diarylike.mapper;

import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.domain.diary.diarylike.entity.DiaryLike;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiaryLikeMapper {

    public DiaryLikeDto.GetDiaryLikesResponse toGetDiaryLikesResponse(List<DiaryLike> diaryLikes) {
        List<DiaryLikeDto.DiaryLikedUserDto> likedUsers = diaryLikes.stream()
                .map(diaryLike -> DiaryLikeDto.DiaryLikedUserDto.builder()
                        .userId(diaryLike.getUser().getUserId().toString())
                        .userNickname(diaryLike.getUser().getUserNickname())
                        .userProfileImage(diaryLike.getUser().getUserProfileImage())
                        .build())
                .toList();

        return DiaryLikeDto.GetDiaryLikesResponse.builder()
                .totalLikes(diaryLikes.size())
                .likedUsers(likedUsers)
                .build();
    }

}
