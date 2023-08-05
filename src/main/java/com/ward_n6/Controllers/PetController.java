package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;
import com.ward_n6.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Operation(summary = "Добавление животного в список", description = "нужно заполнить все поля карточки животного в Body")
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        try {
            Pet newPet = petService.addPet(pet);
            return ResponseEntity.ok(newPet);
        } catch (PutToMapException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{petId}")
    @Operation(summary = "Показать одно животное по id", description = "нужно указать id животного")
    public ResponseEntity<Pet> getPet(@PathVariable int petId) {
        Pet pet = petService.getPetById(petId);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{petId}")
    @Operation(summary = "Отредактировать карточку животного",
            description = "нужно указать id и заполнить все поля карточки животного в Body")
    public ResponseEntity<Pet> editPet(@PathVariable int petId, @RequestBody Pet pet) {
        try {
            Pet newPet = petService.editPetById(petId, pet);
            return ResponseEntity.ok(newPet);
        } catch (EditMapException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{petId}")
    @Operation(summary = "Удалить одно животное из списка", description = "нужно указать id животного")
    public ResponseEntity<Void> deletePet(@PathVariable int petId) {
        try {
            petService.deletePetById(petId);
            return ResponseEntity.ok().build();
        } catch (DeleteFromMapException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Удалить из списка всех животных - приют закрывается")
    public ResponseEntity<Void> deleteAllPets() {
        petService.deleteAllFromPet();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Показать всех животных приюта")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> allPets = petService.getAllPets();
        if (allPets.size() > 0) {
            return ResponseEntity.ok(allPets);
        }
        return ResponseEntity.notFound().build();
    }
}
