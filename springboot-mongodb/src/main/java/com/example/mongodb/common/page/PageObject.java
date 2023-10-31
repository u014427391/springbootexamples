package com.example.mongodb.common.page;

import lombok.Data;

@Data
public class PageObject {
    // 当前页
    private long pageIndex;
    // 当前页数
    private long pageRowNum;
    // 总页数
    private long totalPage;
    // 总数量
    private long totalRow;

}
