package com.luxoft.osh.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<Tag> tags = new ArrayList<>();

    @JsonProperty("tags")
    public List<String> listOfTagsNames() {
        return getTagsNames();
    }

    private List<String> getTagsNames() {
        List<String> listOfTagsNames = new ArrayList<>();
        for (Tag tag : tags) {
            listOfTagsNames.add(tag.getName());
        }
        return listOfTagsNames;
    }
}
