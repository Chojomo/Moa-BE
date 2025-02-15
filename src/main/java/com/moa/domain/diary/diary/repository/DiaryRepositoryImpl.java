package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.dto.QDiaryDto_UserDiaryData;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.moa.domain.diary.diary.entity.QDiary.diary;

@AllArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DiaryDto.UserDiaryData> findUserDiaryList(UUID userId, Integer pageNumber, Integer pageSize) {
        List<DiaryDto.UserDiaryData> result = queryFactory
                .select(new QDiaryDto_UserDiaryData(
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
