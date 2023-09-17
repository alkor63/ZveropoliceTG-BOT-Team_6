package com.ward_n6.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    private final Dog dog = new Dog(2L, PetsType.DOG, PetsSex.MALE, "Rex",
            LocalDate.of(2023, 1, 05), "Метис", null);
    @Test
    public void testCatFactory() {
        long petId = 1;
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

    @Test
    public void testDogFactory() {
        long petId = 1;
        long chatId = 1234567890;
        String test = "test";
//        when(petsOwnerFactories.catFactory(petId, chatId)).thenReturn(cat);
        when(sessionFactory.openSession()).thenReturn(session);

        when(session.get(Dog.class, petId)).thenReturn(dog);
        doNothing().when(chatMessager).sendMessage(anyLong(), anyString());
        when(petsOwnerFactories.dogFactory(petId, chatId)).thenReturn(dog);

        Dog resultDog = petsOwnerFactories.dogFactory(petId, chatId); // почему налл?

        assertEquals(dog, resultDog);

//        verify(chatMessager, never()).sendMessage(anyLong(), anyString());
        verify(session).close();
        verify(sessionFactory).close();
    }
    private  final Owner owner = new Owner(2L, "Vanya", "Pupkin", "don't call");
    @Test
    public void testOwnerFactory(){
        long ownerId = 1;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(Owner.class, ownerId)).thenReturn(owner);
        when(petsOwnerFactories.ownerFactory(ownerId)).thenReturn(owner);
        doNothing().when(chatMessager).sendMessage(anyLong(), anyString());
        Owner resultOwner = petsOwnerFactories.ownerFactory(ownerId);

        assertEquals(owner, resultOwner);

        verify(session).close();
        verify(sessionFactory).close();
    }

    private final PetsOwner petsOwner = new PetsOwner(1L, 23L, LocalDate.of(2023,9,23),
            LocalDate.of(2023,9,23).plusDays(30));
    @Test
    public void testPetsOwnerFactory(){
        long petsOwnerId = 1;
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(PetsOwner.class, petsOwnerId)).thenReturn(petsOwner);
        when(petsOwnerFactories.petsOwnerFactory(petsOwnerId)).thenReturn(petsOwner);
        doNothing().when(chatMessager).sendMessage(anyLong(), anyString());
        PetsOwner resultPetsOwner = petsOwnerFactories.petsOwnerFactory(petsOwnerId);

        assertEquals(petsOwner, resultPetsOwner);

        verify(session).close();
        verify(sessionFactory).close();
    }
}
