package com.moa.domain.diary.diaryimage.entity;

import com.moa.domain.common.CreatedAt;
import com.moa.domain.diary.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "DIARY_IMAGE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage extends CreatedAt {

    @Id
    @UuidGenerator
    @Column(name = "image_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @Comment("다이어리 이미지 UUID")
    private UUID imageId;

    @Column(name = "image_url")
    @Comment("이미지 url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_uuid")
    @Comment("다이어리 UUID")
    private Diary diary;

    @Builder
    public DiaryImage(String imageUrl, Diary image) {
        this.imageUrl = imageUrl;
        this.diary = image;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
