package com.ward_n6.enums;

public enum PetsSex {
    FEM("женс."),
    MALE("муж.");

    private String title;

    PetsSex(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
