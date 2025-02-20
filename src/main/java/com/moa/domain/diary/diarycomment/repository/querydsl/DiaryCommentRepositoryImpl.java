package com.moa.domain.diary.diarycomment.repository.querydsl;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.entity.QDiaryComment;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;

import java.util.UUID;

import static com.moa.domain.diary.diarycomment.entity.QDiaryComment.diaryComment;

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

    private BooleanExpression commentIdEq(UUID commentId) {
        return commentId != null ? diaryComment.diaryCommentId.eq(commentId) : null;
    }

    private BooleanExpression hasNotDeletedChild(QDiaryComment child) {
        return child.deletedAt.isNull();
    }

}
