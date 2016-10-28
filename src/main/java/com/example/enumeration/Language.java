package com.example.enumeration;

public enum Language {

    ARMENIA  ("Armenia"),
    RUSSIA   ("Russia");

    private String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
