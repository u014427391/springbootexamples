package com.example.redis.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {

    // 当前页
    private Integer pageNum = 1;
    // 一页的条数
    private Integer pageSize = 10;

    @JsonIgnore
    private Page pages;

    public void initPage() {
        this.pages = PageHelper.startPage(pageNum , pageSize);
    }

    public PageDataBean loadData(List dataList) {
        return new PageDataBean(dataList , pages.getTotal() , pageNum , pageSize);
    }

    public PageBean setPageBean(org.springframework.data.domain.Page page) {
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        Optional.ofNullable(page).ifPresent(e->{
            Page<?> pageHelper = new Page<>();
            pageHelper.setTotal(page.getTotalElements());
            pageHelper.setPageNum(pageNum);
            pageHelper.setPageSize(pageSize);
            pageBean.setPages(pageHelper);
        });
        return pageBean;
    }

}
