package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.repository.PetRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet")
@Tag(name = "Список животных приюта", description = "CRUD-операции с животными")
public class PetController {
    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    @PostMapping("Добавление кошки")
    public ResponseEntity<Cat> addCat(Cat cat) {
        petRepository.save(cat);
        return ResponseEntity.ok(cat);
    }

    @GetMapping("Поиск кошки")
    public ResponseEntity<String> searchCat(long x) {
        Optional<Pet> search = petRepository.findById(x);
        if (search.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(search.orElseThrow().toString());
    }

    @PutMapping("/{petId}")
    @Operation(summary = "Отредактировать карточку животного",
            description = "нужно указать id и заполнить все поля карточки животного в Body")
    public ResponseEntity<Pet> editPet(@PathVariable int petId, @RequestBody Pet pet) throws NotFoundException {
        long longId = petId;
        Optional optionalPet = petRepository.findById(longId);
        if (!optionalPet.isPresent()) {
            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + petId);
        }
        Pet existingPet = (Pet) optionalPet.get();

        existingPet.setPetName(pet.getPetName());
        existingPet.setBread(pet.getBread());
        existingPet.setPetsType(pet.getPetsType());
        existingPet.setPetBirthDay(pet.getPetBirthDay());

        return ResponseEntity.ok().body(petRepository.save(pet));

    }

    @DeleteMapping("/{petId}")
    @Operation(summary = "Удалить одно животное из списка", description = "нужно указать id животного")
    public ResponseEntity<Pet> deletePet(@PathVariable int petId) throws NotFoundException {
        long longId = petId;
        Optional<Pet> optionalPet = petRepository.findById(longId);
        if (optionalPet.isPresent()) {
            petRepository.deleteById(longId);
            return ResponseEntity.ok().body(optionalPet.get());
        }
        throw new EntityNotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = " + petId);
    }

    @DeleteMapping
    @Operation(summary = "Удалить из списка всех животных - приют закрывается")
    public ResponseEntity<Void> deleteAllPets() {
        petRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Показать всех кошек приюта")
    public String allCats() {
        List<Pet> info = petRepository.findAll();
        return info.toString();
    }
}
