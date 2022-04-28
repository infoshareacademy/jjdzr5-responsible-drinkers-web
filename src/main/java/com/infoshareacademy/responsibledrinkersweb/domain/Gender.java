package com.infoshareacademy.responsibledrinkersweb.domain;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String name;

    public String getName() {
        return name;
    }

    Gender(String name) {
        this.name = name;
    }
}
