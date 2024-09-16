package com.moa.domain.follow.repository;

import com.moa.domain.follow.entity.Follow;
import com.moa.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    Optional<Follow> findFollowByFollowerAndFollowing(User follower, User following);

}
