package com.moa.domain.diary.diarycomment.exception;

import com.moa.global.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiaryCommentExceptionCode implements BaseExceptionCode {

    COMMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST.value(), "댓글이 이미 삭제되었습니다.", "COMMENT_ALREADY_DELETED"),
    COMMENT_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "댓글을 찾을 수 없습니다.", "COMMENT_NOT_EXISTS"),;

    private final Integer status;
    private final String message;
    private final String errorCode;

}
