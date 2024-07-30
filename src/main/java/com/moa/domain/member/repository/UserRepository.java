package com.moa.domain.member.repository;

import com.moa.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from USER u join fetch u.roles where u.userEmail = :userEmail")
    Optional<User> findUserByUserEmail(String userEmail);

    @Query("select u from USER u join fetch u.roles where u.userId = :userId")
    Optional<User> findUserByUserId(UUID userId);

}
