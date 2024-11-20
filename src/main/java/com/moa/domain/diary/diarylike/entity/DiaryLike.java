package com.moa.domain.diary.diarylike.entity;

import com.moa.domain.common.CreatedAt;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "DIARY_LIKE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryLike extends CreatedAt {

    @Id
    @UuidGenerator
    @Column(name = "like_id")
    @Comment("좋아요 식별 UUID")
    private UUID likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("유저 UUID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    @Comment("다이어리 식별 ID")
    private Diary diary;

    @Builder
    public DiaryLike(User user, Diary diary) {
        this.user = user;
        this.diary = diary;
    }

}
