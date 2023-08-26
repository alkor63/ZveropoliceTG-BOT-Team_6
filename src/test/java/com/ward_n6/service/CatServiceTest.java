package com.ward_n6.service;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.service.pets.CatService;
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

public class CatServiceTest {
    private CatService catService;

    @Mock
    private CatRepository catsCrud;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatService(catsCrud);
    }

    @Test
    public void testAddCat() {
        PetsSex petsSex = PetsSex.MALE;
        PetsType petsType = PetsType.CAT;
        Cat cat = new Cat();

        Cat savedCat = new Cat();
        savedCat.setPetsSex(petsSex);

        when(catsCrud.save(cat)).thenReturn(savedCat);

        catService.addCat(petsSex, petsType, cat);

        verify(catsCrud).save(cat);
        assertEquals(petsSex, cat.getPetsSex());
    }

    @Test
    public void testFindCat() {
        long id = 1;
        Cat cat = new Cat();
        Optional<Cat> optionalCat = Optional.of(cat);

        when(catsCrud.findById(id)).thenReturn(Optional.of(cat));

        Optional<Cat> result = Optional.ofNullable(catService.findCat(id));

        assertEquals(optionalCat, result);
    }

    @Test
    public void testDeleteCat() {
        long id = 1;
        Assertions.assertNull(catService.deleteCat(id));

    }


    @Test
    public void testAllCats() {
        List<Cat> expectedCats = new ArrayList<>();
        Cat cat1 = new Cat();
        Cat cat2 = new Cat();
        expectedCats.add(cat1);
        expectedCats.add(cat2);
        when(catsCrud.findAll()).thenReturn(expectedCats);
        List<Cat> actualCats = catService.allCats();

        assertEquals(expectedCats.size(), actualCats.size());
        for (int i = 0; i < expectedCats.size(); i++) {
            assertEquals(expectedCats.get(i), actualCats.get(i));
        }
    }

    @Test
    public void testChange() {
        long id = 1;
        Cat cat = new Cat();
        PetsSex petsSex = PetsSex.FEM;
        PetsType petsType = PetsType.CAT;

        Cat expected = new Cat();
        expected.setId(id);
        expected.setPetsSex(petsSex);

        when(catsCrud.save(cat)).thenReturn(expected);

        Cat result = catService.change(id, petsSex, petsType, cat);

        assertEquals(expected, result);
    }
}