package com.danilermolenko.boot.models;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.danilermolenko.boot.models.Permissions.*;

import java.util.Set;
import java.util.stream.Collectors;

public enum Roles {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, COURSE_READ, COURSE_WRITE)),
    ADMIN_TRAINEE(Sets.newHashSet(USER_READ, COURSE_READ));
    private final Set<Permissions> permissions;

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    Roles(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream().map(e -> new SimpleGrantedAuthority(e.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
