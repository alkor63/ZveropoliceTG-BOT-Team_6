package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.OwnerService;
import com.ward_n6.service.OwnerServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

        @Autowired
        MockMvc mockMvc;
        @Autowired
        ObjectMapper mapper;

        @MockBean
        OwnerService ownerService;
        // создаем несколько объектов тестируемого класса
        Owner owner1 = new Owner(1L,"Pierre", "Goodman", "+79211111111");
        Owner owner2 = new Owner(2L,"Michael", "Verbin", "+79222222222");
        Owner owner3 = new Owner(3L,"Sofiya", "Normann", "+79233333333");

        @Test
        public void testGetOwnerById() {
            when(ownerService.findOwnerById(3)).thenReturn(owner3);
            Owner ownerById = ownerService.findOwnerById(3);
            Assertions.assertNotEquals(ownerById, null);
            Assertions.assertEquals(ownerById.getFirstName(), "Sofiya");
            Assertions.assertEquals(ownerById.getPhoneNumber(), "+79233333333");
        }
        @Test
        public void getAllOwnersTest() throws Exception {
            //тест для метода getAll()
            List owners = new ArrayList<>(Arrays.asList(owner1, owner2, owner3));

            Mockito.when(ownerService.getAllOwners()).thenReturn(owners);

            mockMvc.perform(MockMvcRequestBuilders.get("/owner")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[1].firstName", Matchers.is("Michael")));
        }

        @Test
        public void createOwnerTest() throws Exception {
            //тест для метода save()


            Mockito.when(ownerService.createOwner(owner3)).thenReturn(owner3);

            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/owner")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(owner3));

            mockMvc.perform(mockRequest)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.firstName", Matchers.is("Sofiya")));
        }

        @Test
        public void editOwnerByIdTest() throws Exception {
            //тест для метода editById()
            Owner editedOwner = Owner.builder()
                    .id(1L)
                    .firstName("Вася")
                    .lastName("Пупкин")
                    .phoneNumber("O+79244444444")
                    .build();

            Mockito.when(ownerService.findOwnerById(1)).thenReturn(owner1);
            Mockito.when(ownerService.createOwner(editedOwner)).thenReturn(editedOwner);

            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/owner")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(this.mapper.writeValueAsString(editedOwner));

            mockMvc.perform(mockRequest)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.nutrition", Matchers.is("Whiskas")));
        }


        @Test
        public void deleteOwnerByIdTest() throws Exception{
            Mockito.when(ownerService.deleteOwnerById(2)).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders
                            .delete(("/owner/2"))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        public void deleteOwnerByIdTestWhenReturnFalse()throws Exception{
            Mockito.when(ownerService.deleteOwnerById(5)).thenReturn(false);
            mockMvc.perform(MockMvcRequestBuilders
                            .delete(("/owner/5"))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }