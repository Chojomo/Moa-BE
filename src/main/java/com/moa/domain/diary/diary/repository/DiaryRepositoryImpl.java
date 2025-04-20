package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.query.QUserDiaryDto;
import com.moa.domain.diary.diary.dto.query.QUserLikedDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diary.entity.QDiary;
import com.moa.domain.member.entity.QUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.moa.domain.diary.diary.entity.QDiary.diary;
import static com.moa.domain.diary.diarylike.entity.QDiaryLike.diaryLike;
import static com.moa.domain.member.entity.QUser.user;

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

    @Override
    public Page<Diary> findAllWithUser(Pageable pageable) {
        List<Diary> result = queryFactory
                .select(diary)
                .from(diary)
                .join(diary.user, user).fetchJoin()
                .where(diary.diaryStatus.eq((byte) 2))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diary.count())
                .from(diary)
                .where(diary.diaryStatus.eq((byte) 2));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
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

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        String property = pageable.getSort().iterator().next().getProperty();

        return switch (property) {
            case "viewCount" -> new OrderSpecifier<>(Order.DESC, diary.viewCount);
            case "likeCount" -> new OrderSpecifier<>(Order.DESC, diary.likeCount);
            case "commentCount" -> new OrderSpecifier<>(Order.DESC, diary.commentCount);
            default -> new OrderSpecifier<>(Order.DESC, diary.publishedAt);
        };
    }

}
