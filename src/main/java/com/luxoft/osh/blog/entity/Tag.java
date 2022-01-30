package com.luxoft.osh.blog.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table( name = "tags" )
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_gen")
    @SequenceGenerator(name = "tag_id_gen", sequenceName = "tag_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts = new ArrayList<>();

    @JsonProperty("posts")
    public List<Long> listOfPostIdS() {
        return getPostIdS();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", posts=" + getPostIdS() +
                '}';
    }


    private List<Long> getPostIdS() {
        List<Long> listOfPostIdS = new ArrayList<>();
        for (Post post : posts) {
            listOfPostIdS.add(post.getId());
        }
        return listOfPostIdS;
    }

}
