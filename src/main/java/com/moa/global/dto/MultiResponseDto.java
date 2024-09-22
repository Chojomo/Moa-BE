package com.moa.global.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MultiResponseDto<T> {

    public MultiResponseDto(Integer status, T data, Page page) {
        this.status = status;
        this.data = data;
        this.pageInfo = new PageInfo(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );
    }

    private Integer status;
    private T data;
    private PageInfo pageInfo;

    @Getter
    private class PageInfo {
        private Integer page;
        private Integer pageSize;
        private Integer totalPages;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
        private Integer currentPageElements;

        public PageInfo(Integer page, Integer pageSize, Integer totalPages, Long totalElements, Boolean isFirst, Boolean isLast, Integer currentPageElements) {
            this.page = page;
            this.pageSize = pageSize;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
            this.isFirst = isFirst;
            this.isLast = isLast;
            this.currentPageElements = currentPageElements;
        }
    }

}

