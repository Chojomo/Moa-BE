package com.moa.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    HttpStatus httpStatus;
    Integer status;
    String message;
    T data;
    PageInfo pageInfo;

    public ApiResponse(HttpStatus httpStatus, Integer status, T data, PageInfo pageInfo) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public ApiResponse(HttpStatus httpStatus, Integer status, T data) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> ofPage(HttpStatus httpStatus, T data, PageInfo pageInfo) {
        return new ApiResponse<>(httpStatus, httpStatus.value(), data, pageInfo);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus, httpStatus.value(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<List<T>> okPage(Page<T> page) {
        return ofPage(HttpStatus.OK, page.getContent(), ofPageInfo(page));
    }

    public static <T> ApiResponse<T> ok() {
        return of(HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, data);
    }

    public static <T> ApiResponse<T> created() {
        return of(HttpStatus.CREATED, null);
    }

    public record PageInfo(Integer page, Integer pageSize, Integer totalPages, Long totalElements, Boolean isFirst,
                            Boolean isLast, Integer currentPageElements) {
    }

    private static <T> PageInfo ofPageInfo(Page<T> page) {
        return new PageInfo(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );
    }

}
