package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.PetsOwnerRepository;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PetsOwnerController.class)
public class PetsOwnerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    PetsOwnerRepository petsOwnerRepository;
    // создаем несколько объектов тестируемого класса
    //java.time.LocalDate dateEnd, LocalDate dateBegin, Long ownerId, Long petId
    PetsOwner petsOwner1 = new PetsOwner(1l, LocalDate.now(), LocalDate.now().plusDays(10), 1L, 1L);
    PetsOwner petsOwner2 = new PetsOwner(2L, LocalDate.now(), LocalDate.now().plusDays(20), 2L, 2L);
    PetsOwner petsOwner3 = new PetsOwner(3L, LocalDate.now(), LocalDate.now().plusDays(30), 5L, 3L);


    @Test
    public void getPetsOwnerByIdTest() throws Exception {
        //тест для метода getById()
        Mockito.when(petsOwnerRepository.findById(petsOwner1.getId())).thenReturn(java.util.Optional.of(petsOwner1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pets_owner/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.ownerId", Matchers.is(1)));
    }

    @Test
    public void getAllPetsOwnersTest() throws Exception {
        //тест для метода getAll()
        List petsOwners = new ArrayList<>(Arrays.asList(petsOwner1, petsOwner2, petsOwner3));

        Mockito.when(petsOwnerRepository.findAll()).thenReturn(petsOwners);

        mockMvc.perform(MockMvcRequestBuilders.get("/pets_owner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].petId", Matchers.is(3)));
    }

    @Test
    public void givenPetsOwner_whenAdd_thenStatus201andPetsOwnerReturned() throws Exception {
        //тест для метода addPetsOwner()
        Mockito.when(petsOwnerRepository.save(Mockito.any())).thenReturn(petsOwner1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pets_owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petsOwner1))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(petsOwner1)));
    }
    
    @Test
    public void givenId_whenGetExistingPetsOwner_thenStatus200andPetsOwnerReturned() throws Exception {
        Mockito.when(petsOwnerRepository.findById(Mockito.any())).thenReturn(Optional.of(petsOwner3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pets_owner/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.ownerId").value(5));
    }
    @Test
    public void givenId_whenGetNotExistingPetsOwner_thenStatus404anExceptionThrown() throws Exception {
        Mockito.when(petsOwnerRepository.findById(Mockito.any())).
                thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pets_owner/4"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(EntityNotFoundException.class));
    }
    @Test
    public void givePetsOwner_whenUpdate_thenStatus200andUpdatedReturns() throws Exception {
        Mockito.when(petsOwnerRepository.save(Mockito.any())).thenReturn(petsOwner1);
        Mockito.when(petsOwnerRepository.findById(Mockito.any())).thenReturn(Optional.of(petsOwner1));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pets_owner/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(petsOwner1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.petId").value(8));
    }
    @Test
    public void givenPetsOwner_whenDeletePetsOwner_thenStatus200() throws Exception {
        Mockito.when(petsOwnerRepository.findById(Mockito.any())).thenReturn(Optional.of(petsOwner2));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pets_owner/2"))
                .andExpect(status().isOk());
    }
    @Test
    public void givenPetsOwner_whenGetPetsOwner_thenStatus200() throws Exception {
        List petsOwners = new ArrayList<>(Arrays.asList(petsOwner1, petsOwner2, petsOwner3));

        Mockito.when(petsOwnerRepository.findAll()).thenReturn(petsOwners);

        mockMvc.perform(MockMvcRequestBuilders.get("/pets_owner")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[1].nutrition", Matchers.is("meat")))
                .andExpect(content().json(mapper.writeValueAsString(petsOwners)));
    }

}
