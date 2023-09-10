package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.owner.OwnerRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(OwnerServiceImpl.class)
class OwnerServiceImplTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OwnerRepository ownerRepository;

    @Autowired
    OwnerServiceImpl ownerService;
    Owner owner = new Owner(1L, "Макс", "Фрай", "8-999-999-99-99");
    @Test
    void testGetOwnerById() {

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Owner ownerById = ownerService.findOwnerById(1);
        Assertions.assertNotEquals(ownerById, null);
        assertEquals(ownerById.getLastName(), "Фрай");
        assertEquals(ownerById.getId(), 1L);
    }

    @Test
    void testGetOwnerByIdNotFound() {
        OwnerServiceImpl ownerService = Mockito.mock(OwnerServiceImpl.class);

        long ownerId = 1;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> ownerService.findOwnerById(ownerId));
    }
    @Test
    void testDeleteOwnerById() {
        OwnerServiceImpl ownerService = Mockito.mock(OwnerServiceImpl.class);
        long ownerId = 1;

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        boolean result = ownerService.deleteOwnerById(ownerId);


        assertTrue(result);
        verify(ownerRepository, times(1)).deleteById(ownerId);
    }

    @Test
    void testDeleteOwnerByIdNotFound() {
        OwnerServiceImpl ownerService = Mockito.mock(OwnerServiceImpl.class);
        // Arrange
        long ownerId = 1;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> ownerService.deleteOwnerById(ownerId));
    }
}