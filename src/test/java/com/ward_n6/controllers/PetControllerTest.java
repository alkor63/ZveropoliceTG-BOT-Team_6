package com.ward_n6.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.pets.PetBaseRepository;
import com.ward_n6.service.interfaces.PetService;
import com.ward_n6.service.pets.CatService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static com.ward_n6.OwnerAndPetConstants.LIST_OF_PETS;
import static com.ward_n6.OwnerAndPetConstants.PET_1;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetBaseRepository petRepository;
    @MockBean
    private PetService petService;
    @MockBean
    private CatService catService;
    private Cat cats;

    public void setup() {
        cats = new Cat(PetsType.CAT, PetsSex.FEM, "Маруся", LocalDate.of(2020,01,03),
                "метис");
    }
    @Test
    public void editPet() throws Exception {
        Mockito.when(petService.addPet(Mockito.any())).thenReturn(LIST_OF_PETS.get(1));
        Mockito.when(petService.getPetById(ArgumentMatchers.anyInt()))
                .thenReturn(LIST_OF_PETS.get(0));
        mockMvc.perform(
                        put("/{petId}/1")
                                .content(objectMapper.writeValueAsString(LIST_OF_PETS.get(1)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value("PetName2"))
                .andExpect(jsonPath("$.petBirthDay").value("2020-10-10"))
                .andExpect(jsonPath("$.bread").value("siamese"));
    }

    @Test

    public void deletePet() throws Exception {
        when(petService.deletePetById(Math.toIntExact(PET_1.getId())))
                .thenReturn(true);
        mockMvc.perform(
                        delete("/{petId}", PET_1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAllPets() {
        petRepository.deleteAll();
        assertEquals(0, LIST_OF_PETS.size());
    }

    @Test
    public void getAllCats() throws Exception {
        Mockito.when(catService.allCats()).thenReturn(Collections.singletonList(cats));
        mockMvc.perform(MockMvcRequestBuilders.get("/allCat"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }
}