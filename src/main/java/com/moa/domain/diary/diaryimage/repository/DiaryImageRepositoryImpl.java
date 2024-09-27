package com.moa.domain.diary.diaryimage.repository;

import com.moa.domain.diary.entity.QDiaryImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DiaryImageRepositoryImpl implements DiaryImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByDiaryId(UUID diaryId) {
        QDiaryImage qDiaryImage = QDiaryImage.diaryImage;

        return queryFactory
                .selectOne()
                .from(qDiaryImage)
                .where(qDiaryImage.diary.diaryId.eq(diaryId))
                .fetchFirst() != null;
    }

}
