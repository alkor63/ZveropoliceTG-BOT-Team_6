package com.ward_n6.enums;

public enum PetsType { // может понадобиться для кнопок
    DOG ("собака"),
    CAT("кошка");

    private String title;

    PetsType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
