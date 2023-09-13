package com.ward_n6.listener;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PetsOwnerFactories.class)
class PetsOwnerFactoriesTest {
    @MockBean
    PetsOwnerFactories petsOwnerFactories;
    @Mock
    ChatMessager chatMessager;
    @Mock
    SessionFactory sessionFactory;
    @Mock
    Session session;

    private final Cat cat = new Cat(1L, PetsType.CAT, PetsSex.FEM, "Rysya",
            LocalDate.of(2022, 1, 03), "Метис", null);
    @Test
    public void testCatFactory() {
        long petId = 1L;
        long chatId = 1234567890;

        when(sessionFactory.openSession()).thenReturn(session);

        when(session.get(Cat.class, petId)).thenReturn(cat);
//        when(petsOwnerFactories.catFactory(petId, chatId)).thenReturn(cat);
        Cat resultCat = petsOwnerFactories.catFactory(petId, chatId); // почему налл?

        assertEquals(cat, resultCat);

        verify(chatMessager, never()).sendMessage(anyLong(), anyString());
        verify(session).close();

        verify(sessionFactory).close();
    }
}
