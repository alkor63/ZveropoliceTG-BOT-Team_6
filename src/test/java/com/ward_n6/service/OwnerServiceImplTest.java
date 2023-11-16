package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.owner.OwnerRepository;
import com.ward_n6.service.owners.OwnerServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Тесты методов овнерСервиса
 */ // ЛИЗА + я
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
    public void shouldCreateOwner() {
        Owner owner = new Owner(1L, "Max", "Ivanov", "+79999999999");
        ownerService.createOwner(owner);
        verify(ownerRepository, times(1)).save(owner);
        ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(ownerArgumentCaptor.capture());
        Owner ownerCreated = ownerArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerCreated.getId());
        assertEquals("Max", ownerCreated.getFirstName());
    }

    @Test
    public void shouldGetAllOwners() {
        Owner owner1 = new Owner(1L, "Max", "Ivanov", "+79999999999");
        Owner owner2 = new Owner(1L, "Ivan", "Petrov", "+79999999998");
        when(ownerRepository.findAll()).thenReturn(Arrays.asList(owner1, owner2));
        List<Owner> ownerList = ownerService.getAllOwners();
        assertEquals(ownerList.size(), 2);
        assertEquals(ownerList.get(0).getFirstName(), "Max");
        assertEquals(ownerList.get(1).getFirstName(), "Ivan");

        assertEquals(ownerList.get(0).getLastName(), "Ivanov");
        assertEquals(ownerList.get(1).getLastName(), "Petrov");

        assertEquals(ownerList.get(0).getPhoneNumber(), "+79999999999");
        assertEquals(ownerList.get(1).getPhoneNumber(), "+79999999998");
    }

    @Test
    public void shouldGetOwnerById() {
        Owner owner = new Owner(5L, "Max", "Ivanov", "+79999999999");
        when(ownerRepository.findById(5L)).thenReturn(Optional.of(owner));
        Owner ownerById = ownerService.findOwnerById(5L);
        Assertions.assertNotEquals(ownerById, null);
        assertEquals(ownerById.getFirstName(), "Max");
        assertEquals(ownerById.getLastName(), "Ivanov");
        assertEquals(ownerById.getPhoneNumber(), "+79999999999");
        assertEquals(ownerById.getId(), 5L);
    }


    @Test
    public void shouldGet400OwnerById() {
        when(ownerRepository.findById(100L)).thenThrow(new RecordNotFoundException("OwnerId Not Found"));
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            ownerService.findOwnerById(100);
        });
        Assertions.assertTrue(exception.getMessage().contains("OwnerId Not Found"));
    }

    @Test
    public void shouldDeleteOwner() {
        Owner owner = new Owner(10L, "Anna", "Ivanova", "+79999990000");
        when(ownerRepository.findById(10L)).thenReturn(Optional.of(owner));
        ownerService.deleteOwnerById(Math.toIntExact(owner.getId()));
        verify(ownerRepository, times(1)).deleteById(owner.getId());
        ArgumentCaptor<Long> ownerArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(ownerRepository).deleteById(ownerArgumentCaptor.capture());
        Long ownerDeleted = ownerArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerDeleted);
        assertEquals(10L, ownerDeleted);
    }

    //***************** редактировние *************
    @Test
    public void editOwnerById_SuccessTest() throws EntityNotFoundException {

        Owner existingOwner = new Owner(1L, "Уатя", "Пиванова", "123456789");

        Owner updatedOwner = new Owner();
        updatedOwner.setId(1L);
        updatedOwner.setFirstName("Катя");
        updatedOwner.setLastName("Иванова");
        updatedOwner.setPhoneNumber("80987654321");

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(existingOwner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(updatedOwner);

        // Act
        Owner result = ownerService.editOwnerById(1L, updatedOwner);

        // Assert
        verify(ownerRepository, times(1)).findById(1L);
        verify(ownerRepository, times(1)).save(updatedOwner);
        assertEquals(updatedOwner.getFirstName(), result.getFirstName());
        assertEquals(updatedOwner.getLastName(), result.getLastName());
        assertEquals(updatedOwner.getPhoneNumber(), result.getPhoneNumber());
    }

    @Test
    public void testEditOwnerById_EntityNotFoundException() {

        Owner owner = new Owner(1L, "Уатя", "Пиванова", "123456789");

        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> ownerService.editOwnerById(1L, owner));
        verify(ownerRepository, times(1)).findById(1L);
        verify(ownerRepository, never()).save(any(Owner.class));
    }
    // **************тест на репозиторий (для покрытия) *****
    @Test
    public void testSave (){
        ownerService.save(owner);
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    public void testGetOwnerById() {
        long id = 1L;
        owner.setId(id);

        when(ownerRepository.getById(id)).thenReturn(owner);

        Owner result = ownerService.getOwnerById(id);

        assertEquals(owner, result);
        verify(ownerRepository).getById(id);
    }
    @Test
    public void testDeleteById() {
        long id = 1L;
        ownerService.deleteById(id);
        verify(ownerRepository).deleteById(id);
    }
}