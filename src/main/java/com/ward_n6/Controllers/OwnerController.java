package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.OwnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owner")
@Tag(name = "Список посетителей приюта",
        description = "CRUD-операции с усыновителями")
public class OwnerController {
    private final OwnerRepository ownerRepository;

    public OwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Operation(summary = "Добавление посетителя в список",
            description = "нужно заполнить все поля карточки посетителя в Body")
    @PostMapping("createOwner")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner)  {
      Owner newOwner = ownerRepository.save(owner);
        return ResponseEntity.ok(newOwner);
    }


    @Operation(summary = "Показать одного усыновителя по id",
            description = "нужно указать id усыновителя")
    @GetMapping("getOwner")
    public ResponseEntity<Owner> getOwner(long ownerId) {
        Owner owner = ownerRepository.getById(ownerId);
        if (owner == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(owner);
    }



    @Operation(summary = "Отредактировать карточку усыновителя",
            description = "нужно указать id и заполнить все поля карточки усыновителя в Body")
    @PutMapping("editOwner")
    public ResponseEntity<Owner> editOwner(@RequestParam long ownerId, @RequestBody Owner owner) {
        Owner newOwner = ownerRepository.save(owner);
        if (newOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newOwner);
    }

    @Operation(summary = "Удалить одного усыновителя из списка",
            description = "нужно указать id усыновителя")
    @DeleteMapping("deleteOwner")
    public ResponseEntity<Void> deleteOwner(@RequestParam long ownerId)  {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        if (optionalOwner.isPresent()) {
            ownerRepository.deleteById(ownerId);
             ResponseEntity.ok().body(optionalOwner.get());
        }
        throw new EntityNotFoundException("Невозможно удалить Owner, т.к. в базе нет owner с id = "+ownerId);
    }

    @Operation(summary = "Показать всех усыновителей приюта")
    @GetMapping("getAllOwners")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> allOwners = ownerRepository.findAll();
        if (allOwners.size() > 0) {
            return ResponseEntity.ok(allOwners);
        }
        return ResponseEntity.notFound().build();
    }


}











