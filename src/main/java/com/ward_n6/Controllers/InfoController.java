package com.ward_n6.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

        import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Информация о приложении", description = "здесь только информация")

public class InfoController {
    @GetMapping
    @Operation(summary = "Приветствие", description = "для проверки запуска программы")
    public String hello() {
        return "Приложение стартовало!";
    }
    @GetMapping("/info")
    @Operation(summary = "Информация о приложении", description = "указаны автор, название и назначение приложения")
    public String info() {
        return "Spring-Boot-приложение с функцией telegram-bot, skyPro Java_14, команда Палата_№6 (Анастасия, Алексей, Елизавета, Роман, Тимур), июль-август 2023г.";
    }
}