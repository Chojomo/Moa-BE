package com.moa.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    public ApiResponse(Integer status) {
        this.status = status;
    }

    HttpStatus httpStatus;
    Integer status;
    String message;
    T data;

    public ApiResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse(HttpStatus httpStatus, Integer status, T data) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus, httpStatus.value(), message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus, httpStatus.value(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> ok() {
        return of(HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> created() {
        return of(HttpStatus.CREATED, null);
    }

}
