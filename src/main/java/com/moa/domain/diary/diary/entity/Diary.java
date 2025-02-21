package com.moa.domain.diary.diary.entity;

import com.moa.domain.common.TimeStamped;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diaryimage.entity.DiaryImage;
import com.moa.domain.diary.diarylike.entity.DiaryLike;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "DIARY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends TimeStamped {

    @Id
    @UuidGenerator
    @Column(name = "diary_id", columnDefinition = "BINARY(16)",  updatable = false, nullable = false)
    @Comment("다이어리 UUID")
    private UUID diaryId;

    @Column(name = "diary_thumbnail")
    @Comment("다이어리 썸네일")
    private String diaryThumbnail;

    @Column(name = "diary_title")
    @Comment("다이어리 제목")
    private String diaryTitle;

    @Lob
    @Column(name = "diary_contents", columnDefinition = "TEXT")
    @Comment("다이어리 내용")
    private String diaryContents;

    @Column(name = "is_diary_public")
    @Comment("다이어리 공개, 비공개 여부(0: 비공개, 1: 공개")
    private Boolean isDairyPublic;

    @Column(name = "diary_status")
    @Comment("다이어리 상태 0: 초기화, 1: 임시저장, 2: 저장 완료, 3: 삭제")
    private Byte diaryStatus;

    @Column(name = "view_count")
    @Comment("조회 수")
    private Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    @Comment("유저 UUID")
    private User user;

    @Column(name = "like_count")
    @Comment("좋아요 수")
    private Long likeCount = 0L;

    @Column(name = "comment_count")
    @Comment("댓글 수")
    private Long commentCount = 0L;

    @Column(name = "published_at")
    @Comment("게시 날짜")
    private LocalDateTime publishedAt;

    @Column(name = "deleted_at")
    @Comment("삭제 날짜")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("다이어리 이미지 UUID")
    private List<DiaryImage> diaryImageList = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    @Comment("다이어리 좋아요 UUID")
    private List<DiaryLike> diaryLikeList = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "diary")
    @Comment("다이어리 댓글 UUID")
    private List<DiaryComment> diaryCommentList = new ArrayList<>();

    @Builder
    public Diary(String diaryThumbnail, String diaryTitle, String diaryContents, Boolean isDairyPublic, Byte diaryStatus, User user) {
        this.diaryThumbnail = diaryThumbnail;
        this.diaryTitle = diaryTitle;
        this.diaryContents = diaryContents;
        this.isDairyPublic = isDairyPublic;
        this.diaryStatus = diaryStatus;
        this.user = user;
    }

    public void updateDiary(String diaryTitle, String diaryContents, Boolean isDairyPublic, Byte diaryStatus) {
        Optional.ofNullable(diaryTitle).ifPresent(this::setDiaryTitle);
        Optional.ofNullable(diaryContents).ifPresent(this::setDiaryContents);
        Optional.ofNullable(isDairyPublic).ifPresent(this::setIsDairyPublic);
        this.diaryStatus = (diaryStatus == 0 ? 1 : diaryStatus);
    }

    public void initializeDiary() {
        this.diaryTitle = null;
        this.diaryContents = null;
        this.diaryThumbnail = null;
        this.isDairyPublic = null;
    }

    public void publishDiary(String diaryTitle, String diaryContents, String diaryThumbnail, Boolean isDairyPublic) {
        this.diaryTitle = diaryTitle;
        this.diaryContents = diaryContents;
        this.diaryThumbnail = diaryThumbnail;
        this.isDairyPublic = isDairyPublic;
        this.diaryStatus = 2;
        this.publishedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public void updateDiaryThumbnail(String diaryThumbnail) {
        this.diaryThumbnail = diaryThumbnail;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void deleteDiary() {
        this.diaryStatus = 3;
        this.deletedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public void decrementCommentCount() {
        this.commentCount--;
    }

    public void decrementReplyCount(Integer replyCount) {
        this.commentCount -= replyCount;
    }

}
