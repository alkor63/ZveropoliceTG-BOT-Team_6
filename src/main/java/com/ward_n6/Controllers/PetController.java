package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import com.ward_n6.service.pets.CatService;
import com.ward_n6.service.pets.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pet")
@Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
public class PetController {

    private final CatService catService;
    private final DogService dogService;


    public PetController(CatService catService, DogService dogService) {
        this.catService = catService;
        this.dogService = dogService;
    }

    //---------КОШКИ
    @PostMapping("/addCat/{petsSex}")
    @Operation(summary = "Добавить кошку. ID указывать не надо.")
    public ResponseEntity<Cat> addCat(@PathVariable PetsSex petsSex, Cat cat) {
        PetsType petsType = PetsType.CAT;
        catService.addCat(petsSex, petsType, cat);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Поиск кошки по id")
    @GetMapping("/searchCat")
    public ResponseEntity<String> searchCat(@RequestParam long id) {
        Cat cat = catService.findCat(id);
        if (cat == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(cat.toString());
    }

    @GetMapping("/allCat")
    @Operation(summary = "Показать всех кошек приюта")
    public ResponseEntity<List<Cat>> allCats() {
        List<Cat> cat = catService.allCats();
        if (cat.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Удалить кошку")
    @DeleteMapping("/delCat")
    public ResponseEntity<Cat> deleteCat(@RequestParam long id) {
        Cat result = catService.findCat(id);
        if (result == null) return ResponseEntity.notFound().build();
        Cat cat = catService.deleteCat(id);
        return ResponseEntity.ok(cat);
    }

    @Operation(summary = "Поменять кошку")
    @PutMapping("/changeCat/{id}/{petsSex}")
    public ResponseEntity<Cat> changeCat(@PathVariable long id, @PathVariable PetsSex petsSex, Cat cat) {
        PetsType petsType = PetsType.CAT;
        Cat change = catService.change(id, petsSex, petsType, cat);
        return ResponseEntity.ok(cat);
    }

    //------СОБАКИ
    @PostMapping("/addDog/{petsSex}")
    @Operation(summary = "Добавить собаку. ID указывать не надо.")
    public ResponseEntity<Dog> addDog(@PathVariable PetsSex petsSex, Dog dog) {
        PetsType petsType = PetsType.DOG;
        dogService.addDog(petsSex, petsType, dog);
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Поиск собаки по id")
    @GetMapping("/searchDOg")
    public ResponseEntity<String> searchDog(long id) {
        Dog dog = dogService.findDog(id);
        if (dog == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(dog.toString());
    }

    @GetMapping("/allDog")
    @Operation(summary = "Показать всех собак приюта")
    public ResponseEntity<List<Dog>> allDogs() {
        List<Dog> dog = dogService.allDogs();
        if (dog.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Удалить собаку")
    @DeleteMapping("/delDog")
    public ResponseEntity<Dog> deleteDog(@RequestParam long id) {
        Dog result = dogService.findDog(id);
        if (result == null) return ResponseEntity.notFound().build();
        Dog dog = dogService.deleteDog(id);
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Поменять собаку")
    @PutMapping("/changeDog/{id}/{petsSex}")
    public ResponseEntity<Dog> changeDog(@PathVariable long id, @PathVariable PetsSex petsSex, Dog dog) {
        PetsType petsType = PetsType.DOG;
        Dog change = dogService.change(id, petsSex, petsType, dog);
        return ResponseEntity.ok(change);
    }
}