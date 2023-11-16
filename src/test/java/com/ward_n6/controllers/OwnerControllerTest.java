package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.owner.OwnerRepository;
import com.ward_n6.service.interfaces.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Алексей + я
@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    OwnerRepository ownerRepository;
    @MockBean
    OwnerService ownerService;
    // создаем несколько объектов тестируемого класса
    Owner owner1 = new Owner(1L, "Pierre", "Goodman", "+79211111111");
    Owner owner2 = new Owner(2L, "Michael", "Verbin", "+79222222222");
    Owner owner3 = new Owner(3L, "Sofiya", "Normann", "+79233333333");

    @Test
    public void testGetOwnerById() throws Exception {
        when(ownerService.findOwnerById(3)).thenReturn(owner3);
        Owner ownerById = ownerService.findOwnerById(3);

        Mockito.when(ownerService.findOwnerById(3)).thenReturn(owner3);
        mockMvc.perform(MockMvcRequestBuilders.get("/owner/getOwner/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", Matchers.is("Sofiya")));
    }

    @Test
    public void getAllOwnersTest() throws Exception {
        //тест для метода getAll()
        List owners = new ArrayList<>(Arrays.asList(owner1, owner2, owner3));

        Mockito.when(ownerService.getAllOwners()).thenReturn(owners);

        mockMvc.perform(MockMvcRequestBuilders.get("/owner/getAllOwners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Michael")));
    }

    @Test
    public void createOwnerTest() throws Exception {
        //тест для метода save()

        Mockito.when(ownerService.createOwner(owner3)).thenReturn(owner3);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/owner/createOwner")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(owner3));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", Matchers.is("Sofiya")));
    }


    @Test
    public void testEditOwnerById() throws Exception {
        // Создаем объект Owner для использования в тесте
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Вася");
        owner.setLastName("Пупкин");

        // Определяем поведение сервиса ownerService при вызове метода editOwnerById
        when(ownerService.editOwnerById(eq(1L), any(Owner.class))).thenReturn(owner);

        // Выполняем запрос PUT на owner/editOwner/1 с JSON телом объекта Owner
        mockMvc.perform(put("/owner/editOwner/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"firstName\": \"Вася\", \"lastName\": \"Пупкин\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Вася"))
                .andExpect(jsonPath("$.lastName").value("Пупкин"));

        // Проверяем, что метод editOwnerById был вызван с правильными аргументами
        verify(ownerService).editOwnerById(eq(1L), any(Owner.class));
    }


    @Test
    public void testDeleteOwnerById() throws Exception {
        // Определяем поведение сервиса овнерСервис при вызове метода deleteOwnerById
        when(ownerService.deleteOwnerById(eq(1L))).thenReturn(true);

        // Выполняем запрос DELETE на адрес
        mockMvc.perform(delete("/owner/deleteOwner/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Owner id = 1 удален"));

        // Проверяем, что метод deleteOwnerById был вызван с правильным аргументом
        verify(ownerService).deleteOwnerById(eq(1L));
    }

    @Test
    public void deleteOwnerByIdTestWhenReturnFalse() throws Exception {
        when(ownerService.deleteOwnerById(5)).thenReturn(false);
        mockMvc.perform(delete("/owner/deleteOwner/5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ошибка ID = 5"));
        verify(ownerService).deleteOwnerById(eq(5L));
    }
}