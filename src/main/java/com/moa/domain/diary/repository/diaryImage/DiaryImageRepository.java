package com.moa.domain.diary.repository.diaryImage;

import com.moa.domain.diary.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, UUID>, DiaryImageRepositoryCustom {



}
