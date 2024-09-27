package com.moa.domain.diary.diaryimage.repository;

import com.moa.domain.diary.diaryimage.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, UUID>, DiaryImageRepositoryCustom {



}
