package com.luxoft.osh.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luxoft.osh.blog.entity.Comment;
import com.luxoft.osh.blog.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */
@Data
public class PostFull {
    private Long id;
    private String title;
    private String content;
    private boolean star;
    private Set<Tag> tags = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();

    @JsonProperty("tags")
    public Set<String> listOfTagsNames() {
        return getTagsNames();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star=" + star +
                ", comments=" + comments +
                ", tags=" + getTagsNames() +
                '}';
    }

    private Set<String> getTagsNames() {
        Set<String> listOfTagsNames = new HashSet<>();
        for (Tag tag : tags) {
            listOfTagsNames.add(tag.getName());
        }
        return listOfTagsNames;
    }
}
