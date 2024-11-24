package com.moa.domain.diary.diarycommentlike.entity;

import com.moa.domain.common.CreatedAt;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "DIARY_COMMENT_LIKE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryCommentLike extends CreatedAt {

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
    @JoinColumn(name = "comment_id")
    @Comment("다이어리 댓글 UUID")
    private DiaryComment diaryComment;

    @Builder
    public DiaryCommentLike(User user, DiaryComment diaryComment) {
        this.user = user;
        this.diaryComment = diaryComment;
    }

}
