package com.luxoft.osh.blog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "post" )
public class Post extends BaseEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "star", columnDefinition = "boolean default false")
    private boolean star;


}
