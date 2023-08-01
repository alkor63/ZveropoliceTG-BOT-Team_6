package com.ward_n6.enums;


public enum Buttons {
    START("Начнём"),
    CATS("Кошки"),
    DOGS("Собаки"),
    INFO("Информация о приюте"),
    PASS("Оформление пропуска"),
    VOLUNTEER("Позвать волонтёра");


    private String title;

    Buttons(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Приют: " +
                title + '\'' +
                '}';
    }
}