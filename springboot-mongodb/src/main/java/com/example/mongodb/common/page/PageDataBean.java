package com.example.mongodb.common.page;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDataBean<T> {

    private List<T> dataList = new ArrayList<>();

    private PageObject pageObj = new PageObject();

    public PageDataBean(List<T> dataList , Long totalRow , Integer pageIndex , Integer pageRowNum) {
        this.dataList = dataList;
        pageObj.setPageIndex(pageIndex);
        pageObj.setPageRowNum(pageRowNum);
        pageObj.setTotalRow(totalRow);
        pageObj.setTotalPage(totalRow / pageRowNum + (totalRow % pageRowNum == 0 ? 0 : 1));
    }

}
