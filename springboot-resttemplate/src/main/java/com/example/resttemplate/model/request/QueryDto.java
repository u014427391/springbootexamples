package com.example.resttemplate.model.request;

import lombok.Data;

@Data
public class QueryDto {
    private Integer since;

    private Integer per_page;

}
