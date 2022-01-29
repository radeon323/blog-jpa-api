package com.luxoft.osh.blog.dto;

import com.luxoft.osh.blog.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class PostShort {
    private Long id;
    private String title;
    private String content;
    private boolean star;
    private List<Tag> tag = new ArrayList<>();
}
