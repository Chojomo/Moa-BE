package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, UUID>, DiaryRepositoryCustom {

    Optional<Diary> findDiaryByDiaryStatusAndUser(Byte diaryStatus, User user);

    @Query("SELECT d FROM DIARY d JOIN FETCH d.user WHERE d.diaryId = :diaryId")
    Optional<Diary> findDiaryWithUserById(UUID diaryId);

    @Query("SELECT d FROM DIARY d WHERE d.diaryStatus != 3 AND d.diaryId = :diaryId")
    Optional<Diary> findNotDeletedDiaryById(UUID diaryId);

}
