package com.moa.domain.member.entity;

import com.moa.domain.common.CreatedAt;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "USER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends CreatedAt {

    @Id
    @UuidGenerator
    @Column(name = "user_id", columnDefinition = "BINARY(16)",  updatable = false, nullable = false)
    @Comment("유저 UUID")
    private UUID userId;

    @Column(name = "user_email", nullable = false, unique = true)
    @Comment("유저 계정")
    private String userEmail;

    @Column(name = "user_pw")
    @Comment("유저 비밀번호")
    private String userPassword;

    @Column(name = "username")
    @Comment("유저 네임")
    private String username;

    @Column(name = "user_nickname", nullable = false)
    @Comment("유저 닉네임")
    private String userNickname;

    @Column(name = "user_email_type", nullable = false)
    @Comment("유저 이메일 타입 일반: moa, 구글: google, 카카오: kakao, 네이버: naver")
    private String userEmailType;

    @Column(name = "user_profile_image", nullable = false)
    @Comment("유저 프로필 사진")
    private String userProfileImage;

    @Column(name = "user_introduce")
    @Comment("유저 소개글")
    private String userIntroduce;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_role")
    @Comment("사용자 권한")
    private List<String> roles = new ArrayList<>();

    @Column(name = "last_active_time")
    @Comment("마지막 활동 시간")
    private LocalDateTime lastActiveTime;

    @Builder
    public User(String userEmail, String userPassword, String username, String userNickname, String userEmailType, String userProfileImage, String userIntroduce, List<String> roles, LocalDateTime lastActiveTime) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.username = username;
        this.userNickname = userNickname;
        this.userEmailType = userEmailType;
        this.userProfileImage = userProfileImage;
        this.userIntroduce = userIntroduce;
        this.roles = roles;
        this.lastActiveTime = lastActiveTime;
    }

}
