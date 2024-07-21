package com.moa.domain.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeStamped {

    @CreatedDate
    @Column(name = "created_at")
    @Comment("생성 날짜")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Comment("업데이트 날짜")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = this.createdAt.truncatedTo(ChronoUnit.SECONDS);
        this.updatedAt = this.updatedAt.truncatedTo(ChronoUnit.SECONDS);
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = this.updatedAt.truncatedTo(ChronoUnit.SECONDS);
    }

}
