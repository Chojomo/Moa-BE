package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.query.QUserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.moa.domain.diary.diary.entity.QDiary.diary;

@AllArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserDiaryDto> findUserDiaryList(UUID userId, Integer pageNumber, Integer pageSize) {
        List<UserDiaryDto> result = queryFactory
                .select(new QUserDiaryDto(
                        diary.diaryId,
                        diary.diaryThumbnail,
                        diary.diaryTitle,
                        diary.diaryContents,
                        diary.publishedAt,
                        diary.likeCount,
                        diary.commentCount
                )).from(diary)
                .where(diary.user.userId.eq(userId))
                .offset((long) pageNumber * pageSize)
                .limit(pageSize)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diary.count())
                .from(diary)
                .where(diary.user.userId.eq(userId));

        return PageableExecutionUtils.getPage(result, PageRequest.of(pageNumber, pageSize), countQuery::fetchOne);
    }

}
