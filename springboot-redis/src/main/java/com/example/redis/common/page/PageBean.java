package com.example.redis.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {

    // 当前页
    private Integer pageNum;
    // 一页的条数
    private Integer pageSize;

    @JsonIgnore
    private Page pages;

    public void initPage() {
        this.pages = PageHelper.startPage(pageNum , pageSize);
    }

    public PageDataBean loadData(List dataList) {
        return new PageDataBean(dataList , pages.getTotal() , pageNum , pageSize);
    }

   
}
