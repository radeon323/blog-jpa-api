package com.luxoft.osh.blog.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.luxoft.osh.blog.security.ApiUserPermission.*;

/**
 * @author Oleksandr Shevchenko
 */
@Getter
@RequiredArgsConstructor
public enum ApiUserRole {
    USER(Sets.newHashSet(POST_READ, COMMENT_READ, COMMENT_WRITE)),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, POST_READ, POST_WRITE, COMMENT_READ, COMMENT_WRITE, TAG_WRITE));

    private final Set<ApiUserPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }


}
