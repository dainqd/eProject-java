package com.example.managerfeedback.util;

public enum ERole {
    USER("user"),
    MODERATOR("mod"),
    ADMIN("admin");

    String name;

    ERole(String name) {
        this.name = name;
    }


}