package com.moa.domain.diary.diarycomment.repository.querydsl;

import com.moa.domain.diary.diarycomment.dto.query.QUserCommentDto;
import com.moa.domain.diary.diarycomment.dto.query.UserCommentDto;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.entity.QDiaryComment;
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
import static com.moa.domain.diary.diarycomment.entity.QDiaryComment.diaryComment;
import static com.moa.domain.member.entity.QUser.user;

@AllArgsConstructor
public class DiaryCommentRepositoryImpl implements DiaryCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DiaryComment findCommentWithActiveChildrenComments(UUID commentId) {
        QDiaryComment parent = diaryComment;
        QDiaryComment child = new QDiaryComment("child");

        return queryFactory
                .selectFrom(parent)
                .leftJoin(parent.childrenComments, child).fetchJoin()
                .where(
                        commentIdEq(commentId),
                        child.deletedAt.isNull()
                )
                .fetchOne();
    }

    @Override
    public Page<UserCommentDto> findUserComments(UUID userId, Integer pageNumber, Integer pageSize) {
        List<UserCommentDto> result = queryFactory
                .select(new QUserCommentDto(
                        diaryComment.diaryCommentId,
                        diaryComment.commentContents,
                        diaryComment.diary.diaryId,
                        diaryComment.diary.diaryTitle,
                        diaryComment.createdAt
                ))
                .from(diaryComment)
                .leftJoin(diaryComment.user, user)
                .leftJoin(diaryComment.diary, diary)
                .where(
                        userIdEq(userId),
                        isNotDeletedComment(),
                        isNotDeletedDiary(),
                        isPublishedDiary()
                )
                .orderBy(diaryComment.createdAt.desc())
                .offset((long) pageNumber * pageSize)
                .limit(pageSize)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(diaryComment.count())
                .from(diaryComment)
                .from(diaryComment)
                .leftJoin(diaryComment.user, user)
                .leftJoin(diaryComment.diary, diary)
                .where(
                        userIdEq(userId),
                        isNotDeletedComment(),
                        isNotDeletedDiary(),
                        isPublishedDiary()
                );

        return PageableExecutionUtils.getPage(result, PageRequest.of(pageNumber, pageSize), countQuery::fetchOne);
    }

    private BooleanExpression commentIdEq(UUID commentId) {
        return commentId != null ? diaryComment.diaryCommentId.eq(commentId) : null;
    }

    private BooleanExpression userIdEq(UUID userId) {
        return user.userId.eq(userId);
    }

    private BooleanExpression isNotDeletedComment() {
        return diaryComment.deletedAt.isNull();
    }

    private BooleanExpression isNotDeletedDiary() {
        return diary.deletedAt.isNull();
    }

    private BooleanExpression isPublishedDiary() {
        return diary.diaryStatus.eq((byte) 2);
    }

}
