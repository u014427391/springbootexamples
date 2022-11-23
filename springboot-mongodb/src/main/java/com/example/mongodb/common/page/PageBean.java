package com.example.mongodb.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;

import java.util.List;

@Data
public class PageBean {

    // 当前页
    private Integer pageIndex = 1;
    // 一页的条数
    private Integer pageRowNum = 10;

    @JsonIgnore
    private Page pages;

    public void initPage() {
        this.pages = PageHelper.startPage(pageIndex , pageRowNum);
    }

    public PageDataBean loadData(List dataList) {
        return new PageDataBean(dataList , pages.getTotal() , pageIndex , pageRowNum);
    }


}
