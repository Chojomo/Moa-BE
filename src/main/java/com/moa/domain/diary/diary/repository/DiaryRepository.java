package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, UUID> {

    Optional<Diary> findDiaryByDiaryStatusAndUser(Byte diaryStatus, User user);

    @EntityGraph(attributePaths = {"user", "diaryLikeList"})
    @Query("SELECT d FROM DIARY d WHERE d.diaryStatus = 2 ORDER BY d.publishedAt DESC")
    Page<Diary> findAllWithUserOrderByPublishedAt(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "diaryLikeList"})
    @Query("SELECT d FROM DIARY d WHERE d.diaryStatus = 2 ORDER BY d.totalLikes DESC")
    Page<Diary> findAllWithUserOrderByTotalLikes(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "diaryLikeList"})
    @Query("SELECT d FROM DIARY d WHERE d.diaryStatus = 2 ORDER BY d.viewCounts DESC")
    Page<Diary> findAllWithUserOrderByViewCounts(Pageable pageable);

}
