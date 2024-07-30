package com.moa.domain.member.entity;

import com.moa.domain.common.CreatedAt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends CreatedAt {

    @Id
    @UuidGenerator
    @Column(name = "user_uuid", columnDefinition = "BINARY(16)",  updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "user_email", nullable = false, unique = true)
    @Comment("유저 계정")
    private String userEmail;

    @Column(name = "user_pw", nullable = false)
    @Comment("유저 비밀번호")
    private String userPw;

    @Column(name = "user_nickname", nullable = false)
    @Comment("유저 닉네임")
    private String userNickname;

    @Column(name = "user_email_type", nullable = false)
    @Comment("유저 이메일 타입 0: MOA, 1: 네이버, 2: 구글, 3: 카카오")
    private Byte userEmailType;

    @Column(name = "user_profile_image", nullable = false)
    @Comment("유저 프로필 사진")
    private String userProfileImage;

    @Column(name = "user_introduce")
    @Comment("유저 소개글")
    private String userIntroduce;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_uuid"))
    @Column(name = "user_role")
    @Comment("사용자 권한")
    private List<String> roles = new ArrayList<>();

    @Column(name = "last_active_time")
    @Comment("마지막 활동 시간")
    private LocalDateTime lastActiveTime;

    @Builder
    public User(String userEmail, String userPw, String userNickname, Byte userEmailType, String userProfileImage, String userIntroduce, List<String> roles, LocalDateTime lastActiveTime) {
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userNickname = userNickname;
        this.userEmailType = userEmailType;
        this.userProfileImage = userProfileImage;
        this.userIntroduce = userIntroduce;
        this.roles = roles;
        this.lastActiveTime = lastActiveTime;
    }

}
