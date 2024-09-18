package com.moa.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class SingleResponseDto<T> {

    public SingleResponseDto(Integer status) {
        this.status = status;
    }

    Integer status;
    T data;

}
