package com.luxoft.osh.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table( name = "comments" )
public class Comment extends BaseEntity<Comment>{

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post", referencedColumnName = "id")
    private Post post;

}
