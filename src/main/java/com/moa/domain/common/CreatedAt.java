package com.moa.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreatedAt {

    @CreatedDate
    @Column(name = "created_at")
    @Comment("생성 날짜")
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = this.createdAt.truncatedTo(ChronoUnit.SECONDS);
    }

}
