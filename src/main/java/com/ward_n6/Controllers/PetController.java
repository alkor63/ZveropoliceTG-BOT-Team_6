package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.Dog_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.service.CatService;
import com.ward_n6.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
@Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
public class PetController {
    @Autowired
    private CatService catService;

    @Autowired
    private DogService dogService;

    //---------КОШКИ

    @PostMapping("add_cat/{petsSex}")
    @Operation(summary = "Добавить кошку")
    public ResponseEntity<Cat_2> addCat(@PathVariable PetsSex petsSex, Cat_2 cat_2) {
        catService.addCat(petsSex, cat_2);
        return ResponseEntity.ok(cat_2);
    }

    @Operation(summary = "Поиск кошки по id")
    @GetMapping("searchCat")
    public ResponseEntity<String> searchCat(long id) {
        Cat_2 cat = catService.findCat(id);
        if (cat == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(cat.toString());
    }


    @GetMapping("allCat")
    @Operation(summary = "Показать всех кошек приюта")
    public ResponseEntity<String> allCats() {
        String cat = catService.allCats();
        if (cat.isBlank()) return ResponseEntity.ok().body("Кошек нет в приюте");
        return ResponseEntity.ok(cat);
    }
    @Operation(summary = "Удалить кошку")
    @DeleteMapping("delCat")
    public ResponseEntity<String> deleteCat(@RequestParam long id) {
        String s = catService.deleteCat(id);
        return ResponseEntity.ok(s);
    }
    @Operation(summary = "Поменять кошку")
    @PutMapping("changeCat")
    public ResponseEntity<Cat_2> changeCat(@RequestParam long id, @RequestParam PetsSex petsSex, Cat_2 cat_2) {
        Cat_2 change = catService.change(id, cat_2, petsSex);
        return ResponseEntity.ok(change);
    }


    //------СОБАКИ


    @PostMapping("add_dog/{petsSex}")
    @Operation(summary = "Добавить собаку")
    public ResponseEntity<Dog_2> addDog(@PathVariable PetsSex petsSex, Dog_2 dog_2) {
        dogService.addDog(petsSex, dog_2);
        return ResponseEntity.ok(dog_2);
    }

    @Operation(summary = "Поиск собаки по id")
    @GetMapping("searchDog")
    public ResponseEntity<String> searchDog(long id) {
        Dog_2 dog = dogService.findDog(id);
        if (dog == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(dog.toString());
    }


    @GetMapping("allDogs")
    @Operation(summary = "Показать всех собак приюта")
    public ResponseEntity<String> allDogs() {
        String dog = dogService.allDogs();
        if (dog.isBlank()) return ResponseEntity.ok().body("Собак нет в приюте");
        return ResponseEntity.ok(dog);
    }

    @Operation(summary = "Удалить собаку")
    @DeleteMapping("delDog")
    public ResponseEntity<String> deleteDog(@RequestParam long id) {
        String s = dogService.deleteDog(id);
        return ResponseEntity.ok(s);
    }
    @Operation(summary = "Поменять собаку")
    @PutMapping("changeDog")
    public ResponseEntity<Dog_2> changeDog(@RequestParam long id, @RequestParam PetsSex petsSex, Dog_2 dog_2) {
        Dog_2 change = dogService.change(id, dog_2, petsSex);
        return ResponseEntity.ok(change);
    }

}
