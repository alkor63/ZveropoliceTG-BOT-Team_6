package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.owner.PetsOwnerRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(PetsOwnerServiceImpl.class)
class PetsOwnerServiceImplTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private PetsOwnerRepository petsOwnerRepository;
    @Autowired
    private PetsOwnerServiceImpl petsOwnerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testEditDateEndPetsOwnerById() throws NotFoundException {
        // Создаем тестовые данные
        long petsOwnerId = 1;
        LocalDate newDateEnd = LocalDate.now();

        // Создаем мок объекта PetsOwner
        PetsOwner petsOwner = new PetsOwner();
        petsOwner.setId(petsOwnerId);
        petsOwner.setEndDate(LocalDate.now().minusDays(1));

        // Настраиваем мок объекта PetsOwnerRepository
        when(petsOwnerRepository.findById(petsOwnerId)).thenReturn(Optional.of(petsOwner));
        when(petsOwnerRepository.save(petsOwner)).thenReturn(petsOwner);

        // Вызываем тестируемый метод
        PetsOwner result = petsOwnerServiceImpl.editDateEndPetsOwnerById(petsOwnerId, newDateEnd);

        // Проверяем результат
        assertNotNull(result);
        assertEquals(newDateEnd, result.getEndDate());

        // Проверяем вызовы методов мока
        verify(petsOwnerRepository, times(1)).findById(petsOwnerId);
        verify(petsOwnerRepository, times(1)).save(petsOwner);
    }

    @Test
    public void testEditDateEndPetsOwnerById_ThrowsNotFoundException() throws NotFoundException {

        long petsOwnerId = 1;
        LocalDate newDateEnd = LocalDate.now();

        Mockito.when(petsOwnerRepository.findById(petsOwnerId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> petsOwnerServiceImpl.editDateEndPetsOwnerById(petsOwnerId, newDateEnd));
        verify(petsOwnerRepository, times(1)).findById(1L);
        verify(petsOwnerRepository, never()).save(any(PetsOwner.class));
    }
}


