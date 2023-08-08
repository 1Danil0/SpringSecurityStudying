package com.danilermolenko.boot.models;

public enum Permissions {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");
    private final String permission;

    Permissions(String permissions) {
        this.permission = permissions;
    }

    public String getPermission() {
        return permission;
    }
}
