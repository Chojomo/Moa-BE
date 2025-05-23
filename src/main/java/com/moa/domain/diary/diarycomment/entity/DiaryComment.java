package com.moa.domain.diary.diarycomment.entity;

import com.moa.domain.common.TimeStamped;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "DIARY_COMMENT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryComment extends TimeStamped {

    @Id
    @UuidGenerator
    @Column(name = "comment_id", columnDefinition = "BINARY(16)",  updatable = false, nullable = false)
    @Comment("다이어리 댓글 UUID")
    private UUID diaryCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    @Comment("부모 댓글 식별 ID")
    private DiaryComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiaryComment> childrenComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    @Comment("다이어리 식별 ID")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("댓글 작성자 ID")
    private User user;

    @Lob
    @Column(name = "comment_contents", nullable = false, columnDefinition = "TEXT")
    @Comment("댓글 내용")
    private String commentContents;

    @Column(name = "like_count")
    @Comment("좋아요 수")
    private Long likeCount = 0L;

    @Column(name = "deleted_at")
    @Comment("삭제 시간")
    private LocalDateTime deletedAt;

    @Builder
    public DiaryComment(DiaryComment parentComment, List<DiaryComment> childrenComments, Diary diary, User user, String commentContents) {
        this.parentComment = parentComment;
        this.childrenComments = childrenComments;
        this.diary = diary;
        this.user = user;
        this.commentContents = commentContents;
    }

    public static DiaryComment createComment(Diary diary, User user, String commentContents) {
        return DiaryComment.builder()
                .diary(diary)
                .user(user)
                .commentContents(commentContents)
                .build();
    }

    public static DiaryComment createReply(Diary diary, DiaryComment diaryComment, User user, String commentContents) {
        return DiaryComment.builder()
                .diary(diary)
                .parentComment(diaryComment)
                .user(user)
                .commentContents(commentContents)
                .build();
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void updateCommentContents(String commentContents) {
        this.commentContents = commentContents;
    }

    public void deleteComment() {
        this.deletedAt = LocalDateTime.now().withNano(0);
    }

}
