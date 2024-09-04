package com.moa.domain.diary.entity;

import com.moa.domain.common.TimeStamped;
import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

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

    @Column(name = "diary_title", nullable = false)
    @Comment("다이어리 제목")
    private String diaryTitle;

    @Column(name = "diary_contents", nullable = false)
    @Comment("다이어리 내용")
    private String diaryContents;

    @Column(name = "is_diary_public", nullable = false)
    @Comment("다이어리 공개, 비공개 여부(0: 비공개, 1: 공개")
    private Boolean isDairyPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    @Comment("유저 UUID")
    private User user;

}
