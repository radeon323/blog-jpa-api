package com.luxoft.osh.blog.dto;

import lombok.Data;

@Data
public class PostShort {
    private Long id;
    private String title;
    private String content;
    private boolean star;
}
