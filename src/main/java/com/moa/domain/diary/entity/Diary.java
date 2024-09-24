package com.moa.domain.diary.entity;

import com.moa.domain.common.TimeStamped;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "DIARY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends TimeStamped {

    @Id
    @UuidGenerator
    @Column(name = "diary_uuid", columnDefinition = "BINARY(16)",  updatable = false, nullable = false)
    @Comment("다이어리 UUID")
    private UUID diaryId;

    @Column(name = "diary_thumbnail")
    @Comment("다이어리 썸네일")
    private String diaryThumbnail;

    @Column(name = "diary_title")
    @Comment("다이어리 제목")
    private String diaryTitle;

    @Column(name = "diary_contents")
    @Comment("다이어리 내용")
    private String diaryContents;

    @Column(name = "is_diary_public")
    @Comment("다이어리 공개, 비공개 여부(0: 비공개, 1: 공개")
    private Boolean isDairyPublic;

    @Column(name = "diary_status")
    @Comment("다이어리 상태 0: 초기화, 1: 임시저장, 2: 저장 완료")
    private Byte diaryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    @Comment("유저 UUID")
    private User user;

    @Column(name = "published_at")
    @Comment("게시 날짜")
    private LocalDateTime publishedAt;

    @Builder
    public Diary(String diaryThumbnail, String diaryTitle, String diaryContents, Boolean isDairyPublic, Byte diaryStatus, User user) {
        this.diaryThumbnail = diaryThumbnail;
        this.diaryTitle = diaryTitle;
        this.diaryContents = diaryContents;
        this.isDairyPublic = isDairyPublic;
        this.diaryStatus = diaryStatus;
        this.user = user;
    }

    public void updateDiary(String diaryTitle, String diaryContents, String diaryThumbnail, Boolean isDairyPublic, Byte diaryStatus) {
        Optional.ofNullable(diaryTitle).ifPresent(this::setDiaryTitle);
        Optional.ofNullable(diaryContents).ifPresent(this::setDiaryContents);
        Optional.ofNullable(diaryThumbnail).ifPresent(this::setDiaryThumbnail);
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

}
