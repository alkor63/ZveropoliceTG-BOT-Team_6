package com.ward_n6.controllers;

import com.ward_n6.Controllers.InfoController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.ward_n6.OwnerAndPetConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class InfoControllerTest {

    private InfoController out = new InfoController();


    @Test
    public void shouldGetHelloBotMessage()
    {
        String result = out.hello();
        assertTrue(result.contains(MESSAGE));
        assertEquals(MESSAGE, result);
        assertEquals("Приложение стартовало!", out.hello());
    }
    @Test
    public void shouldGetBotInfoMessage()
    {
        String result = out.info();
        assertTrue(result.contains(INFO));
        assertEquals(INFO, result);
        assertEquals("Spring-Boot-приложение с функцией telegram-bot, skyPro Java_14, команда Палата_№6 (Анастасия, Алексей, Елизавета, Роман, Тимур), июль-август 2023г.", out.info());
    }


}
