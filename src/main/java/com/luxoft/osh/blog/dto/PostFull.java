package com.luxoft.osh.blog.dto;

import com.luxoft.osh.blog.entity.Comment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostFull {
    private Long id;
    private String title;
    private String content;
    private boolean star;
    private List<Comment> comments = new ArrayList<>();
}
