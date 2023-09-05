package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;

import com.ward_n6.repository.pets.PetBaseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet")
@Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
public class PetController {

    @Autowired
    private PetBaseRepository petBaseRepository;

//    public PetController(PetRepository petRepository) {
//        this.petRepository = petRepository;
//    }


    @PostMapping("Добавление кошки")
    public ResponseEntity<Cat> addCat(Cat cat) {
        petBaseRepository.save(cat);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("Поиск кошки")
    public ResponseEntity<String> searchCat(long x) {
        Optional<Pet> search = petBaseRepository.findById(x);
        if (search.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(search.orElseThrow().toString());
    }

    @PutMapping("/{petId}")
    @Operation(summary = "Отредактировать карточку животного",
            description = "нужно указать id и заполнить все поля карточки животного в Body")
    public ResponseEntity<Pet> editPet(@PathVariable int petId, @RequestBody Pet pet) throws NotFoundException {
        Optional<Pet> optionalPet = petBaseRepository.findById((long) petId);
        if (optionalPet.isEmpty()) {
            throw new NotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + petId);
        }
        Pet existingPet = optionalPet.get();

        existingPet.setPetName(pet.getPetName());
        existingPet.setBread(pet.getBread());
        existingPet.setPetsType(pet.getPetsType());
        existingPet.setPetBirthDay(pet.getPetBirthDay());
        Pet oPet = (Pet) petBaseRepository.save(pet);
        return ResponseEntity.ok().body(oPet);

    }

    @DeleteMapping("/{petId}")
    @Operation(summary = "Удалить одно животное из списка", description = "нужно указать id животного")
    public ResponseEntity<Pet> deletePet(@PathVariable int petId) throws NotFoundException {
        Optional<Pet> optionalPet = petBaseRepository.findById((long) petId);
        if (optionalPet.isPresent()) {
            petBaseRepository.deleteById((long) petId);
            return ResponseEntity.ok().body(optionalPet.get());
        }
        throw new NotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = " + petId);
    }

    @DeleteMapping
    @Operation(summary = "Удалить из списка всех животных - приют закрывается")
    public ResponseEntity<Void> deleteAllPets() {
        petBaseRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Показать всех кошек приюта")
    public String allCats() {
        List<Pet> info = petBaseRepository.findAll();
        return info.toString();
    }
}
