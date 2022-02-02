package com.luxoft.osh.blog.rest;

import com.luxoft.osh.blog.dto.TagShort;
import com.luxoft.osh.blog.entity.Tag;
import com.luxoft.osh.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Shevchenko
 */
@RestController
@RequestMapping("/api/v1/posts/")
@RequiredArgsConstructor
public class TagRestControllerV1 {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final TagService tagService;

    @GetMapping(value = "tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tag>> findAllTags() {
        logger.info("TagRestControllerV1 findAllTags");

        List<Tag> tags = tagService.findAll();

        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<List<Tag>> responseEntity = new ResponseEntity<>(tags, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @PostMapping(value = "tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> saveTag(@RequestBody @Valid Tag tag) {
        logger.info("TagRestControllerV1 saveTag {}", tag);

        if (tag == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        tagService.save(tag);

        ResponseEntity<Tag> responseEntity = new ResponseEntity<>(tag, HttpStatus.CREATED);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping(value = "tags/{tagId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> deleteTagById(@PathVariable("tagId") Long tagId) {
        logger.info("TagRestControllerV1 deleteTagById");

        tagService.deleteById(tagId);

        ResponseEntity<Tag> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    @GetMapping(value = "{postId}/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TagShort>> findAllByPostId(@PathVariable("postId") Long postId) {
        logger.info("TagRestControllerV1 getAllComments");

        List<Tag> tags = tagService.findAllByPostId(postId);

        if (tags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<TagShort> tagsShort = new ArrayList<>();
        for (Tag tag : tags) {
            tagsShort.add(dtoConvertToShort(tag));
        }

        ResponseEntity<List<TagShort>> responseEntity = new ResponseEntity<>(tagsShort, HttpStatus.OK);
        logger.info("Status Code {}", responseEntity.getStatusCode());
        logger.info("Request Body {}", responseEntity.getBody());
        return responseEntity;
    }

    private TagShort dtoConvertToShort(Tag tag) {
        TagShort tagShort = new TagShort();
        tagShort.setId(tag.getId());
        tagShort.setName(tag.getName());
        return tagShort;
    }


}
