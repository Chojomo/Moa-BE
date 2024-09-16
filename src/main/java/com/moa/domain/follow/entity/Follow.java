package com.moa.domain.follow.entity;

import com.moa.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "FOLLOW")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @UuidGenerator
    @Column(name = "follow_uuid", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    @Comment("팔로우 UUID")
    private UUID followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_uuid", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_uuid", nullable = false)
    private User following;

    @Column(name = "followed_at", nullable = false)
    private LocalDateTime followedAt;

    @Builder
    public Follow(User follower, User following, LocalDateTime followedAt) {
        this.follower = follower;
        this.following = following;
        this.followedAt = followedAt;
    }

}
