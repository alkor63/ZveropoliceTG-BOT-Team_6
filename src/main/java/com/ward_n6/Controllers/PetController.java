package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.CatsCrud;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.service.interfaces.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
    @RequestMapping("/pet")
    @Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
    public class PetController {
        private final PetService petService;

        @Resource
        private CatsCrud catsCrud;

    public PetController(PetService petService) {
        this.petService = petService;
    }


//        @PostMapping
//        @Operation(summary = "Добавление животного в список", description = "нужно заполнить все поля карточки животного в Body")
//        public ResponseEntity<Pet> createPet(@RequestBody Pet pet) throws JsonProcessingException, PutToMapException {
//            Pet newPet = petRepository.addPet(pet);
//            return ResponseEntity.ok(newPet);
//        }
        //    @PostMapping("/json")
//    @Operation(summary = "Добавление рецепта из файла в книгу", description = "будем читать файл pets.json")
//    public ResponseEntity<Pet> readPetFromJsonFile() {
//        return ResponseEntity.ok().build();
//    }
//        @GetMapping("/{petId}")
//        @Operation(summary = "Показать одно животное по id", description = "нужно указать id животного")
//        public ResponseEntity<Pet> getPet(@PathVariable int petId) {
//            Pet pet = petRepository.getPetById(petId);
//            if (pet == null) {
//                return ResponseEntity.notFound().build();
//            }
//            return ResponseEntity.ok(pet);
//        }


    @PostMapping("Добавление кошки")
    public ResponseEntity<Cat_2> addCat(Cat_2 cat_2) {
        catsCrud.save(cat_2);
        return ResponseEntity.ok(cat_2);
    }
      @GetMapping("Поиск кошки")
        public ResponseEntity<String> searchCat(long x) {
        Optional<Cat_2> search = catsCrud.findById(x);
        if (search.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(search.orElseThrow().toString());
    }

        @PutMapping("/{petId}")
        @Operation(summary = "Отредактировать карточку животного",
                description = "нужно указать id и заполнить все поля карточки животного в Body")
        public ResponseEntity<Pet> editPet(@PathVariable int petId, @RequestBody Pet pet) throws EditMapException {
            Pet newPet = petService.editPetById(petId, pet);
            if (newPet == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(newPet);
        }

        @DeleteMapping("/{petId}")
        @Operation(summary = "Удалить одно животное из списка", description = "нужно указать id животного")
        public ResponseEntity<Void> deletePet(@PathVariable int petId) throws DeleteFromMapException {
            if (petService.deletePetById(petId)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }

        @DeleteMapping
        @Operation(summary = "Удалить из списка всех животных - приют закрывается")
        public ResponseEntity<Void> deleteAllPets() {
            petService.deleteAllFromPet();
            return ResponseEntity.ok().build();
        }

        @GetMapping
        @Operation(summary = "Показать всех кошек приюта")
        public String allCats() {
            Iterable<Cat_2> info = catsCrud.findAll();
            return info.toString();
        }

//        @GetMapping
//        @Operation(summary = "Показать всех животных приюта")
//        public ResponseEntity<List<Pet>> getAllPets() {
//            List<Pet> allPets = petRepository.getAllPets();
//            if (allPets.size() > 0) {
//                return ResponseEntity.ok(allPets);
//            }
//            return ResponseEntity.notFound().build();
//        }


    }
