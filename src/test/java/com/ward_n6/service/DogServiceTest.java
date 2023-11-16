package com.ward_n6.service;

import com.ward_n6.entity.pets.Dog;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.service.pets.DogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DogServiceTest {
    private DogService dogService;

    @Mock
    private DogRepository dogsCrud;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dogService = new DogService(dogsCrud);
    }

    @Test
    public void testAddDog() {
        PetsSex petsSex = PetsSex.MALE;
        PetsType petsType = PetsType.DOG;
        Dog dog = new Dog();

        Dog savedDog = new Dog();
        savedDog.setPetsSex(petsSex);

        when(dogsCrud.save(dog)).thenReturn(savedDog);

        dogService.addDog(petsSex, petsType, dog);

        verify(dogsCrud).save(dog);
        assertEquals(petsSex, dog.getPetsSex());
    }

    @Test
    public void testFindDog() {
        long id = 1;
        Dog dog = new Dog();
        Optional<Dog> optionalDog = Optional.of(dog);

        when(dogsCrud.findById(id)).thenReturn(Optional.of(dog));

        Optional<Dog> result = Optional.ofNullable(dogService.findDog(id));

        assertEquals(optionalDog, result);
    }

    @Test
    public void testDeleteDog() {
        long id = 1;
        Assertions.assertNull(dogService.deleteDog(id));

    }


    @Test
    public void testAllDogs() {
        List<Dog> expectedDogs = new ArrayList<>();
        Dog dog1 = new Dog();
        Dog dog2 = new Dog();
        expectedDogs.add(dog1);
        expectedDogs.add(dog2);
        when(dogsCrud.findAll()).thenReturn(expectedDogs);
        List<Dog> actualDogs = dogService.allDogs();

        assertEquals(expectedDogs.size(), actualDogs.size());
        for (int i = 0; i < expectedDogs.size(); i++) {
            assertEquals(expectedDogs.get(i), actualDogs.get(i));
        }
    }

    @Test
    public void testChange() {
        long id = 1;
        Dog dog = new Dog();
        PetsSex petsSex = PetsSex.FEM;
        PetsType petsType = PetsType.DOG;

        Dog expected = new Dog();
        expected.setId(id);
        expected.setPetsSex(petsSex);

        when(dogsCrud.save(dog)).thenReturn(expected);

        Dog result = dogService.change(id, petsSex, petsType, dog);

        assertEquals(expected, result);
    }

}