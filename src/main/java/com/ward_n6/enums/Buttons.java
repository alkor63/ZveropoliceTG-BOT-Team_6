package com.ward_n6.enums;


public enum Buttons {
        DOGS("Собаки"),
        CATS("Кошки");

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