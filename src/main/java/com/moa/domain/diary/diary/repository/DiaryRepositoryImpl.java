package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.query.QUserDiaryDto;
import com.moa.domain.diary.diary.dto.query.QUserLikedDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.moa.domain.diary.diary.entity.QDiary.diary;
import static com.moa.domain.diary.diarylike.entity.QDiaryLike.diaryLike;

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
                ))
                .from(diary)
                .where(
                        userIdEq(userId),
                        isPublic(),
                        isNotDeleted()
                )
                .orderBy(diary.publishedAt.desc())
                .offset((long) pageNumber * pageSize)
                .limit(pageSize)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diary.count())
                .from(diary)
                .where(
                        userIdEq(userId),
                        isPublic(),
                        isNotDeleted()
                );

        return PageableExecutionUtils.getPage(result, PageRequest.of(pageNumber, pageSize), countQuery::fetchOne);
    }

    @Override
    public Page<UserLikedDiaryDto> findUserLikedDiaryList(UUID userId, Integer pageNumber, Integer pageSize) {
        List<UserLikedDiaryDto> result = queryFactory
                .select(new QUserLikedDiaryDto(
                        diary.diaryId,
                        diary.diaryThumbnail,
                        diary.diaryTitle,
                        diary.diaryContents,
                        diary.publishedAt,
                        diary.likeCount,
                        diary.commentCount
                ))
                .from(diaryLike)
                .leftJoin(diaryLike.diary, diary)
                .where(
                        likedByUserId(userId),
                        isPublic(),
                        isNotDeleted()
                )
                .orderBy(diaryLike.createdAt.desc())
                .offset((long) pageNumber * pageSize)
                .limit(pageSize)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diaryLike.count())
                .from(diaryLike)
                .leftJoin(diaryLike.diary, diary)
                .where(
                        likedByUserId(userId),
                        isPublic(),
                        isNotDeleted()
                );

        return PageableExecutionUtils.getPage(result, PageRequest.of(pageNumber, pageSize), countQuery::fetchOne);
    }

    private BooleanExpression likedByUserId(UUID userId) {
        return userId != null ? diaryLike.user.userId.eq(userId) : null;
    }

    private BooleanExpression userIdEq(UUID userId) {
        return userId != null ? diary.user.userId.eq(userId) : null;
    }

    private BooleanExpression isPublic() {
        return diary.isDairyPublic.isTrue();
    }

    private BooleanExpression isNotDeleted() {
        return diary.deletedAt.isNull();
    }

}
