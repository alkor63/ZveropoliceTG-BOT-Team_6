package com.ward_n6.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.repository.pets.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetRepository petRepository;


//    @Test
//    void editPet() throws Exception {
//        Mockito.when(petRepository.addPet(Mockito.any())).thenReturn(LIST_OF_PETS.get(1));
//        Mockito.when(petRepository.getPetById(ArgumentMatchers.anyInt()))
//                .thenReturn(LIST_OF_PETS.get(0));
//        mockMvc.perform(
//                        put("/{petId}/1")
//                                .content(objectMapper.writeValueAsString(LIST_OF_PETS.get(1)))
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.petName").value("PetName2"))
//                .andExpect(jsonPath("$.petBirthDay").value("10.10.2020"))
//                .andExpect(jsonPath("$.bread").value("siamese"));
//    }
//
//    @Test
//    void deletePet() throws Exception {
//        when(petRepository.deletePetById(Math.toIntExact(PET_1.getId()))).thenReturn(true);
//        mockMvc.perform(
//                        delete("/{petId}", PET_1.getId()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteAllPets() {
//        petRepository.deleteAllFromPet();
//        assertEquals(0, LIST_OF_PETS.size());
//    }

}