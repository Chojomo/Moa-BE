package com.moa.domain.follow.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserFollowDto {

    private UUID userId;
    private String userNickname;
    private String userProfileImage;

}
