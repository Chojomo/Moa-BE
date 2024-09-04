package com.moa.domain.diary.repository;

import com.moa.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, UUID> {



}
