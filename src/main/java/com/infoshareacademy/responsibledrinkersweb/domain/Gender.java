package com.infoshareacademy.responsibledrinkersweb.domain;

public enum Gender {
    MALE("Mężczyzna"),
    FEMALE("Kobieta"),
    OTHER("Inna");

    private final String name;

    public String getName() {
        return name;
    }

    Gender(String name) {
        this.name = name;
    }
}
