package com.example.redis.common.page;

import lombok.Data;

@Data
public class PageObject {
    // 当前页
    private long pageNum;
    // 当前页数
    private long pageSize;
    // 总页数
    private long totalPage;
    // 总数量
    private long totalCount;

}
