package com.ward_n6.service;
import com.ward_n6.entity.pets.Dog_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.DogsCrud;
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
import static org.mockito.Mockito.*;

public class DogServiceTest {
    private DogService dogService;

    @Mock
    private DogsCrud dogsCrud;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dogService = new DogService(dogsCrud);
    }

    @Test
    public void testAddDog() {
        PetsSex petsSex = PetsSex.MALE;
        Dog_2 dog_2 = new Dog_2();

        Dog_2 savedDog = new Dog_2();
        savedDog.setPetsSex(petsSex);

        when(dogsCrud.save(dog_2)).thenReturn(savedDog);

        dogService.addDog(petsSex, dog_2);

        verify(dogsCrud).save(dog_2);
        assertEquals(petsSex, dog_2.getPetsSex());
    }

    @Test
    public void testFindDog() {
        long id = 1;
        Dog_2 dog_2 = new Dog_2();
        Optional<Dog_2> optionalDog_2 = Optional.of(dog_2);

        when(dogsCrud.findById(id)).thenReturn(Optional.of(dog_2));

        Optional<Dog_2> result = Optional.ofNullable(dogService.findDog(id));

        assertEquals(optionalDog_2, result);
    }

    @Test
    public void testDeleteDog() {
        long id = 1;
        Assertions.assertNull(dogService.deleteDog(id));

    }


    @Test
    public void testAllDogs() {
        List<Dog_2> expectedDogs = new ArrayList<>();
        Dog_2 dog1 = new Dog_2();
        Dog_2 dog2 = new Dog_2();
        expectedDogs.add(dog1);
        expectedDogs.add(dog2);
        when(dogsCrud.findAll()).thenReturn(expectedDogs);
        List<Dog_2> actualDogs = dogService.allDogs();

        assertEquals(expectedDogs.size(), actualDogs.size());
        for (int i = 0; i < expectedDogs.size(); i++) {
            assertEquals(expectedDogs.get(i), actualDogs.get(i));
        }
    }

    @Test
    public void testChange() {
        long id = 1;
        Dog_2 dog_2 = new Dog_2();
        PetsSex petsSex = PetsSex.FEM;

        Dog_2 expected = new Dog_2();
        expected.setId(id);
        expected.setPetsSex(petsSex);

        when(dogsCrud.save(dog_2)).thenReturn(expected);

        Dog_2 result = dogService.change(id, dog_2, petsSex);

        assertEquals(expected, result);
    }

}
