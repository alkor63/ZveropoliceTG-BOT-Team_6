package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import com.ward_n6.service.pets.CatService;
import com.ward_n6.service.pets.DogService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ward_n6.enums.PetsType.CAT;
import static com.ward_n6.enums.PetsType.DOG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestExecutionListeners
@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
//    private ObjectMapper mapper;


    @MockBean
    private CatService catService;
    @MockBean
    private DogService dogService;

    //**************** ДОБАВИТЬ ****************
    @Test
    public void testAddCat() { // working

        PetsSex petsSex = PetsSex.FEM;
        Cat cat = new Cat();

        CatService catService = Mockito.mock(CatService.class);
        Mockito.when(catService.addCat(petsSex, CAT, cat)).thenReturn(cat);

        PetController petController = new PetController(catService, dogService);

        ResponseEntity<Cat> response = petController.addCat(petsSex, cat);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cat, response.getBody());
    }

    @Test
    public void testAddDog() { // working

        PetsSex petsSex = PetsSex.FEM;
        Dog dog = new Dog();

        DogService dogService = Mockito.mock(DogService.class);
        Mockito.when(dogService.addDog(petsSex, DOG, dog)).thenReturn(dog);

        PetController petController = new PetController(catService, dogService);

        ResponseEntity<Dog> response = petController.addDog(petsSex, dog);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dog, response.getBody());
    }

    Cat cat = new Cat(1, CAT, PetsSex.FEM, "Кыся", LocalDate.of(2020, 03, 12),
            "метис", null);
    Cat cat2 = new Cat(2, CAT, PetsSex.FEM, "Муся", LocalDate.of(2021, 03, 12),
            "метис", null);

    //**************** ИЗМЕНИТЬ ****************
    @Test
    public void changeCatTest() throws Exception {// working
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);
        long id = 1;
        PetsSex petsSex = PetsSex.MALE;
        cat.setOwnerId(2L);

        when(catService.change(id, petsSex, PetsType.CAT, cat)).thenReturn(cat);

        ResponseEntity<Cat> response = petController.changeCat(id, petsSex, cat);

        assertEquals(cat, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, cat.getOwnerId()); // Проверяем, что ownerId изменился на 2
    }

    Dog dog = new Dog(1, DOG, PetsSex.MALE, "Шарик", LocalDate.of(2022, 03, 12),
            "метис", null);
    Dog dog2 = new Dog(1, DOG, PetsSex.MALE, "Тузя", LocalDate.of(2021, 03, 12),
            "метис", null);

    @Test
    public void changeDogTest() throws Exception { // рабочий
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);
        long id = 1;
        PetsSex petsSex = PetsSex.MALE;
        dog.setPetName("ШАР");

        when(dogService.change(id, petsSex, DOG, dog)).thenReturn(dog);

        ResponseEntity<Dog> response = petController.changeDog(id, petsSex, dog);

        assertEquals(dog, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ШАР", dog.getPetName()); // Проверяем, что ownerId изменился на 2
    }

    //**************** НАЙТИ ****************
    @Test
    void searchCatTest() {
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);

        long id = 1;

        when(catService.findCat(id)).thenReturn(cat);

        ResponseEntity<String> response = petController.searchCat(id);

        assertEquals(cat.toString(), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void searchCatNotFoundTest() {
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);

        long id = 3;
        when(catService.findCat(id)).thenReturn(null);

        ResponseEntity<String> response = petController.searchCat(id);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void searchDogTest() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);

        long id = 1;

        when(dogService.findDog(id)).thenReturn(dog);

        ResponseEntity<String> response = petController.searchDog(id);

        assertEquals(dog.toString(), response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void searchDogNotFoundTest() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);

        long id = 3;
        when(dogService.findDog(id)).thenReturn(null);

        ResponseEntity<String> response = petController.searchDog(id);

        assertEquals(404, response.getStatusCodeValue());
    }

    //**************** СПИСОК ВСЕХ ****************
    @Test
    void allCatsTest() {
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);
        List<Cat> cats = new ArrayList<>();
        cats.add(cat);
        cats.add(cat2);

        when(catService.allCats()).thenReturn(cats);

        ResponseEntity<List<Cat>> response = petController.allCats();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cats, response.getBody());
    }

    @Test
    void testAllCatsNotFound() {
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);
        when(catService.allCats()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Cat>> response = petController.allCats();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void allDogsTest() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);
        List<Dog> dogs = new ArrayList<>();
        dogs.add(dog);
        dogs.add(dog2);

        when(dogService.allDogs()).thenReturn(dogs);

        ResponseEntity<List<Dog>> response = petController.allDogs();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dogs, response.getBody());
    }

    @Test
    void testAllDogsNotFound() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);
        when(dogService.allDogs()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Dog>> response = petController.allDogs();

        assertEquals(404, response.getStatusCodeValue());
    }

//**************** УДАЛИТЬ ****************
@Test
void deleteCatTest() {
    CatService catService = Mockito.mock(CatService.class);
    PetController petController = new PetController(catService, dogService);
    long catId = 1;
    when(catService.findCat(catId)).thenReturn(cat);
    when(catService.deleteCat(catId)).thenReturn(cat);

    ResponseEntity<Cat> response = petController.deleteCat(catId);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(cat, response.getBody());
}

    @Test
    void deleteCatNotFoundTest() {
        CatService catService = Mockito.mock(CatService.class);
        PetController petController = new PetController(catService, dogService);
        long catId = 1;

        when(catService.findCat(catId)).thenReturn(null);

        ResponseEntity<Cat> response = petController.deleteCat(catId);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteDogTest() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);
        long dogId = 1;
        when(dogService.findDog(dogId)).thenReturn(dog);
        when(dogService.deleteDog(dogId)).thenReturn(dog);

        ResponseEntity<Dog> response = petController.deleteDog(dogId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dog, response.getBody());
    }

    @Test
    void deleteDogNotFoundTest() {
        DogService dogService = Mockito.mock(DogService.class);
        PetController petController = new PetController(catService, dogService);
        long dogId = 1;

        when(dogService.findDog(dogId)).thenReturn(null);

        ResponseEntity<Dog> response = petController.deleteDog(dogId);

        assertEquals(404, response.getStatusCodeValue());
    }
}