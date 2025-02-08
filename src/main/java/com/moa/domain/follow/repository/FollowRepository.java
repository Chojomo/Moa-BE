package com.moa.domain.follow.repository;

import com.moa.domain.follow.dto.query.UserFollowerDto;
import com.moa.domain.follow.entity.Follow;
import com.moa.domain.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    Optional<Follow> findFollowByFollowerAndFollowing(User follower, User following);

    @Query("SELECT new com.moa.domain.follow.dto.query.UserFollowerDto(" +
            "f.follower.userId, f.follower.userNickname, f.follower.userProfileImage" +
            ") " +
            "FROM FOLLOW f " +
            "LEFT JOIN f.following " +
            "WHERE f.follower = :follower")
    Page<UserFollowerDto> findFollowByFollower(User follower, Pageable pageable);

}
