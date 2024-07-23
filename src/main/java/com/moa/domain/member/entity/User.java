package com.moa.domain.member.entity;

import com.moa.domain.common.CreatedAt;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity(name = "USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends CreatedAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Comment("유저 식별 ID")
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true)
    @Comment("유저 계정")
    private String userEmail;

    @Column(name = "user_pw", nullable = false)
    @Comment("유저 비밀번호")
    private String userPw;

    @Column(name ="user_email_type", nullable = false)
    @Comment("유저 이메일 타입 0: MOA, 1: 네이버, 2: 구글, 3: 카카오")
    private Byte userEmailType;

    @Column(name = "user_profile_image", nullable = false)
    @Comment("유저 프로필 사진")
    private String userProfileImage;

    @Column(name = "user_introduce")
    @Comment("유저 소개글")
    private String userIntroduce;

    @Column(name = "last_active_time")
    @Comment("마지막 활동 시간")
    private LocalDateTime lastActiveTime;

    @Builder
    public User(String userEmail, String userPw, Byte userEmailType, String userProfileImage, String userIntroduce, LocalDateTime lastActiveTime) {
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userEmailType = userEmailType;
        this.userProfileImage = userProfileImage;
        this.userIntroduce = userIntroduce;
        this.lastActiveTime = lastActiveTime;
    }

}
