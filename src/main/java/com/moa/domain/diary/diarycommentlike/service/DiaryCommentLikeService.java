package com.moa.domain.diary.diarycommentlike.service;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.member.entity.User;

public interface DiaryCommentLikeService {

    boolean toggleLikeOnComment(User user, DiaryComment diaryComment);

}
