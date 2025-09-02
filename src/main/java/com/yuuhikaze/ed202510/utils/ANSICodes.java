package com.yuuhikaze.ed202510.utils;

public enum ANSICodes {
    RESET("\u001B[0m"),
    BOLD("\u001B[1m"),
    DIM("\u001B[2m");

    private final String code;

    ANSICodes(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
