package com.ward_n6.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.Impl.OwnerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;

@WebMvcTest(OwnerService.class)
class OwnerServiceTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OwnerRepository ownerRepository;
    @Autowired
    OwnerServiceImpl ownerService;

    @Test
    public void shouldCreateOwner() {
        Owner owner = new Owner(1L,"Max", "Ivanov", "+79999999999");
        ownerService.createOwner(owner);
        verify(ownerRepository, times(1)).save(owner);
        ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(ownerArgumentCaptor.capture());
        Owner ownerCreated = ownerArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerCreated.getId());
        Assertions.assertEquals("Max", ownerCreated.getFirstName());
    }

    @Test
    public void shouldGetAllOwners() {
        Owner owner1 = new Owner(1L,"Max", "Ivanov", "+79999999999");
        Owner owner2 = new Owner(1L,"Ivan", "Petrov", "+79999999998");
        when(ownerRepository.findAll()).thenReturn(Arrays.asList(owner1, owner2));
        List<Owner> ownerList = ownerService.getAllOwners();
        Assertions.assertEquals(ownerList.size(), 2);
        Assertions.assertEquals(ownerList.get(0).getFirstName(), "Max");
        Assertions.assertEquals(ownerList.get(1).getFirstName(), "Ivan");
        Assertions.assertEquals(ownerList.get(0).getLastName(), "Ivanov");
        Assertions.assertEquals(ownerList.get(1).getFirstName(), "Petrov");
        Assertions.assertEquals(ownerList.get(0).getPhoneNumber(), "Ok");
        Assertions.assertEquals(ownerList.get(1).getPhoneNumber(), "meat");
    }

    @Test
    public void shouldGetOwnerById() {
        Owner owner = new Owner(5L,"Max", "Ivanov", "+79999999999");
        when(ownerRepository.findById(5L)).thenReturn(Optional.of(owner));
        Owner ownerById = ownerService.getOwnerById(7);
        Assertions.assertNotEquals(ownerById, null);
        Assertions.assertEquals(ownerById.getFirstName(), "Max");
        Assertions.assertEquals(ownerById.getLastName(), "Ivanov");
        Assertions.assertEquals(ownerById.getPhoneNumber(), "+79999999999");
        Assertions.assertEquals(ownerById.getId(), 5L);
    }


    @Test
    public void shouldGet400OwnerById() {
        when(ownerRepository.findById(100L)).thenThrow(new RecordNotFoundException("OwnerId Not Found"));
        Exception exception = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            ownerService.getOwnerById(100);
        });
        Assertions.assertTrue(exception.getMessage().contains("OwnerId Not Found"));
    }

    @Test
    public void shouldDeleteOwner() {
        Owner owner = new Owner(10L,"Anna", "Ivanova", "+79999990000");
        when(ownerRepository.findById(10L)).thenReturn(Optional.of(owner));
        ownerService.deleteOwnerById(Math.toIntExact(owner.getId()));
        verify(ownerRepository, times(1)).deleteById(owner.getId());
        ArgumentCaptor<Long> ownerArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(ownerRepository).deleteById(ownerArgumentCaptor.capture());
        Long ownerDeleted = ownerArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerDeleted);
        Assertions.assertEquals(10L, ownerDeleted);
    }

}