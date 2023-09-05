package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.owner.OwnerRepository;
import org.hamcrest.Matchers;
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
import java.util.Optional;

import static com.ward_n6.OwnerAndPetConstants.OWNER_1;
import static com.ward_n6.OwnerAndPetConstants.OWNER_2;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {
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


//   НЕ РАБОТАЕТ, ЧТО МОЖНО ИЗМЕНИТЬ?
//    @Test
//        //выведение ошибки на метод createOwner
//    void createOwner_ShouldReturn400() throws Exception {
//        when(ownerRepository.save(INCORRECT_OWNER)).thenReturn(false);
//        this.mockMvc.perform(
//                        post("/cat")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(INCORRECT_OWNER)))
//                .andExpect(status().isBadRequest());
//    }


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


//   РАБОТАЕТ НЕПРАВИЛЬНО, ЧТО МОЖНО СДЕЛАТЬ?
//    @Test
//    //выведение ошибки на метод findById
//    void findById_ShouldReturn404() throws Exception {
//        when(ownerRepository.findById(5L)).thenReturn(Optional.empty());
//        this.mockMvc.perform(
//                        get("getOwner", 5))
//                .andExpect(status().isNotFound());
//    }


    @Test
        //тест выбрасывания исключения для findById
    void ShouldReturnExceptionToGetOwner() throws Exception {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        this.mockMvc.perform(
                        get("getOwner", 5))
                .andExpect(status().isNotFound());
    }

//    КАЖЕТСЯ, РАБОТАЕТ НЕПРАВИЛЬНО, ЧТО МОЖНО СДЕЛАТЬ?
//    @Test
//    void findById_ShouldReturn404() throws Exception {
//        when(ownerRepository.findById(5L)).thenReturn(Optional.empty());
//        this.mockMvc.perform(
//                        get("getOwner}", 5))
//                .andExpect(status().isNotFound());
//    }


    @Test
        //тест метода по редактированию данных владельца
    void ShouldEditOwner() throws Exception {
        Owner editedOwner = Owner.builder()
                .id(1L)
                .firstName("Vasya")
                .lastName("Popov")
                .phoneNumber("+79000000000")
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

    //ПОДСКАЖИТЕ, ПОЖАЛУЙСТА, КАК МОЖНО РЕАЛИЗОВАТЬ ИЛИ КАКИМ ПУТЁМ СТОИТ ПОЙТИ, ЧТОБЫ НАПИСАТЬ ТЕСТ НА ВЫВЕДЕНИЕ ОШИБКИ К МЕТОДУ НА ОБНОВЛЕНИЕ КАРТОЧКИ?

    @Test
        //тест метода по удалению владельца
    void ShouldDeleteOwner() throws Exception {
        Mockito.when(ownerRepository.findById(OWNER_2.getId())).thenReturn(Optional.of(OWNER_2));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("deleteOwner"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//   НЕ РАБОТАЕТ ТЕСТ
//    @Test
//    void deleteById_ShouldReturn404() throws Exception {
//        when(ownerRepository.deleteById(INCORRECT_OWNER.getId())).thenReturn(false);
//        mockMvc.perform(
//                        delete("deleteOwner", INCORRECT_OWNER.getId()))
//                .andExpect(status().isNotFound());
//    }


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

//   МОЖНО ЛИ ТАК РЕАЛИЗОВАТЬ ТЕСТ НА ВЫВЕДЕНИЕ ОШИБКИ?
//    @Test
//    void findAll_ShouldReturn404() throws Exception {
//        when(ownerRepository.findAll()).thenReturn(new ArrayList<>());
//        this.mockMvc.perform(
//                        get("getAllOwners"))
//                .andExpect(status().isNotFound());
//    }


}