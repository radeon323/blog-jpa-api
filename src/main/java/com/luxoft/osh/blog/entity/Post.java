package com.luxoft.osh.blog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Oleksandr Shevchenko
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table( name = "posts" )
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_gen")
    @SequenceGenerator(name = "post_id_gen", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "star", columnDefinition = "boolean default false")
    private boolean star;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

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
