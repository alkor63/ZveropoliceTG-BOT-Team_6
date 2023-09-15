package com.ward_n6.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.service.BotMessageService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@TestExecutionListeners
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PetsOwnerFactories.class)
class PetsOwnerFactoriesTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;

    @Mock
    ChatMessager chatMessager;
    @InjectMocks
    PetsOwnerFactories petsOwnerFactories;

    private final Cat cat = new Cat(1L, PetsType.CAT, PetsSex.FEM, "Rysya",
            LocalDate.of(2022, 1, 03), "Метис", null);

    @Test
    public void testCatFactory() {
        long petId = 1L;
        long chatId = 1234567890;
        String test = "test";
//        when(petsOwnerFactories.catFactory(petId, chatId)).thenReturn(cat);
        when(sessionFactory.openSession()).thenReturn(session);

        when(session.get(Cat.class, petId)).thenReturn(cat);
        doNothing().when(chatMessager).sendMessage(anyLong(), anyString());
        when(petsOwnerFactories.catFactory(petId, chatId)).thenReturn(cat);


        Cat resultCat = petsOwnerFactories.catFactory(petId, chatId); // почему налл?

        assertEquals(cat, resultCat);

//        verify(chatMessager, never()).sendMessage(anyLong(), anyString());
        verify(session).close();

        verify(sessionFactory).close();
    }


}
