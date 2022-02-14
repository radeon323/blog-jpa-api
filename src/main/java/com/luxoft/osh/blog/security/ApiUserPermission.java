package com.luxoft.osh.blog.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@RequiredArgsConstructor
public enum ApiUserPermission {
    USER_READ("users:read"),
    USER_WRITE("users:write"),
    POST_READ("posts:read"),
    POST_WRITE("posts:write"),
    COMMENT_READ("comments:read"),
    COMMENT_WRITE("comments:write"),
    TAG_READ("tags:read"),
    TAG_WRITE("tags:write");

    private final String permission;

}
