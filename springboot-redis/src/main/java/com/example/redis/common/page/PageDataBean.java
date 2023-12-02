package com.example.redis.common.page;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDataBean<T> {

    private List<T> dataList = new ArrayList<>();

    private PageObject pageObj = new PageObject();

    public PageDataBean(List<T> dataList , Long totalCount , Integer pageSize , Integer pageNum) {
        this.dataList = dataList;
        pageObj.setPageNum(pageNum);
        pageObj.setPageSize(pageSize);
        pageObj.setTotalCount(totalCount);
        pageObj.setTotalPage(totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1));
    }

}
