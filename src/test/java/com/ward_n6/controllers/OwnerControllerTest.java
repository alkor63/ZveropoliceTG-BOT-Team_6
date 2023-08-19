package com.ward_n6.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ward_n6.Controllers.OwnerController;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.interfaces.OwnerService;
import javassist.NotFoundException;
import liquibase.pro.packaged.L;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ward_n6.OwnerAndPetConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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


    @Test
    //тест метода по добавлению владельца
    void ShouldCreateOwner() throws Exception {
        Mockito.when(ownerRepository.save(Mockito.any())).thenReturn(OWNER_1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("createOwner")
                                .content(objectMapper.writeValueAsString(OWNER_1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(OWNER_1)));
    }



    @Test
    //тест метода по поиска владельца при наличии его id
    void ShouldGetOwner() throws Exception {
        Mockito.when(ownerRepository.findById(OWNER_1.getId())).thenReturn(java.util.Optional.of(OWNER_1));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("getOwner", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(OWNER_1.getId())))
                .andExpect(jsonPath("$.first_name", Matchers.is(OWNER_1.getFirstName())))
                .andExpect(jsonPath("$.last_name", Matchers.is(OWNER_1.getLastName())))
                .andExpect(jsonPath("$.phone_number", Matchers.is(OWNER_1.getPhoneNumber())));
    }

    @Test
    //тест выбрасывания исключения для findById
    void ShouldReturnExceptionToGetOwner() throws Exception {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(
                        get("getOwner", 5))
                .andExpect(status().isNotFound());
    }

    @Test
    //тест метода по редактированию данных владельца
    void ShouldEditOwner() throws Exception {
        Owner editedOwner = Owner.builder()
                .ownerId(1L)
                .first_name("Vasya")
                .last_name("Popov")
                .phone_number("+79000000000")
                .build();
        when(ownerRepository.findById(OWNER_1.getId())).thenReturn(Optional.of(OWNER_1));
        when(ownerRepository.save(editedOwner)).thenReturn(editedOwner);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("editOwner")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(editedOwner));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", Matchers.is("Vasya")))
                .andExpect(jsonPath("$.last_name", Matchers.is("Popov")))
                .andExpect(jsonPath("$.phone_number", Matchers.is("+79000000000")));
    }


    @Test
    //тест метода по удалению владельца
    void ShouldDeleteOwner() throws Exception {
        Mockito.when(ownerRepository.findById(OWNER_2.getId())).thenReturn(Optional.of(OWNER_2));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("deleteOwner"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    //тест метода по получению всех владельцев
    void ShouldGetAllOwners() throws Exception {
        List allOwners = new ArrayList<>(Arrays.asList(OWNER_1, OWNER_2));

        Mockito.when(ownerRepository.findAll()).thenReturn(allOwners);

        mockMvc.perform(MockMvcRequestBuilders.get("getAllOwners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


}