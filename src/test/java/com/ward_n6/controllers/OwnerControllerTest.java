package com.ward_n6.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.Controllers.OwnerController;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.interfaces.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.ward_n6.OwnerAndPetConstants.LIST_OF_OWNERS;
import static com.ward_n6.OwnerAndPetConstants.OWNER_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OwnerRepository ownerRepository;
    private OwnerService ownerService;


    @Test
    void ShouldCreateOwner() throws Exception {
        Mockito.when(ownerService.addOwner(Mockito.any())).thenReturn(LIST_OF_OWNERS.get(0));
        mockMvc.perform(
                        post("/{ownerId}")
                                .content(objectMapper.writeValueAsString(LIST_OF_OWNERS.get(0)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(LIST_OF_OWNERS.get(0))));
    }

    @Test
    void ShouldGetOwner() throws Exception {
        Mockito.when(ownerService.getOwnerById(ArgumentMatchers.anyInt()))
                .thenReturn(LIST_OF_OWNERS.get(0));
        this.mockMvc.perform(
                        get("/{ownerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(OWNER_1.getId())))
                .andExpect(jsonPath("$.first_name", Matchers.is(OWNER_1.getFirstName())))
                .andExpect(jsonPath("$.last_name", Matchers.is(OWNER_1.getLastName())))
                .andExpect(jsonPath("$.phone_number", Matchers.is(OWNER_1.getPhoneNumber())));
    }

//    @Test
//    void ShouldReturnExceptionToGetOwner() throws Exception {
//        when(ownerRepository.getOwnerById(5)).thenReturn(Optional.empty());
//        this.mockMvc.perform(
//                        get("/{id}", 5))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void ShouldEditOwner() throws Exception {
        Mockito.when(ownerService.addOwnerToDB(Mockito.any())).thenReturn(LIST_OF_OWNERS.get(1));
        Mockito.when(ownerService.getOwnerById(ArgumentMatchers.anyInt()))
                .thenReturn(LIST_OF_OWNERS.get(0));
        mockMvc.perform(
                        put("/{ownerId}/1")
                                .content(objectMapper.writeValueAsString(LIST_OF_OWNERS.get(1)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("LastName2"))
                .andExpect(jsonPath("$.firstName").value("FirstName2"));
    }


    @Test
    void ShouldDeleteOwner() throws Exception {
        Mockito.when(ownerService.addOwnerToDB(Mockito.any())).thenReturn(LIST_OF_OWNERS.get(1));
        mockMvc.perform(
                        delete("/{ownerId}"))
                .andExpect(status().isOk());
    }

    @Test
    void ShouldReturnExceptionToDeleteOwner() throws Exception {
        when(ownerService.deleteOwnerById(Math.toIntExact(OWNER_1.getId()))).thenReturn(false);
        mockMvc.perform(
                        delete("/{ownerId}", OWNER_1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void ShouldDeleteAllOwners() {
        ownerService.deleteAllFromOwner();
        assertEquals(0, LIST_OF_OWNERS.size());
    }


    @Test
    void ShouldGetAllOwners() {
        when(ownerService.getAllOwners())
                .thenReturn(LIST_OF_OWNERS);
        assertEquals(status().isOk(), LIST_OF_OWNERS.size());
    }



}