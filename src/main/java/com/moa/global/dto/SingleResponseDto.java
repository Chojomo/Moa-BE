package com.moa.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SingleResponseDto<T> {

    T data;

}
