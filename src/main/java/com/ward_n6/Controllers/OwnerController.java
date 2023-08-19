package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.reports.OwnerReport;
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

    @PostMapping
    @Operation(summary = "Добавление посетителя в список",
            description = "нужно заполнить все поля карточки посетителя в Body")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner)  {
      Owner newOwner = ownerRepository.save(owner);
        //  Owner newOwner = ownerRepository.addOwnerToDB(owner);
        return ResponseEntity.ok(newOwner);
    }

    @GetMapping("/{ownerId}")
    @Operation(summary = "Показать одного усыновителя по id",
            description = "нужно указать id усыновителя")
    public ResponseEntity<Owner> getOwner(@PathVariable int ownerId) {
        long longId = ownerId;
        Owner owner = ownerRepository.getById(longId);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    @PutMapping("/{ownerId}")
    @Operation(summary = "Отредактировать карточку усыновителя",
            description = "нужно указать id и заполнить все поля карточки усыновителя в Body")
    public ResponseEntity<Owner> editOwner(@PathVariable int ownerId, @RequestBody Owner owner) {
        Owner newOwner = ownerRepository.save(owner);
        if (newOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newOwner);
    }

    @DeleteMapping("/{ownerId}")
    @Operation(summary = "Удалить одного усыновителя из списка",
            description = "нужно указать id усыновителя")
    public ResponseEntity<Void> deleteOwner(@PathVariable int ownerId)  {
        long longId = ownerId;
        Optional<Owner> optionalOwner = ownerRepository.findById(longId);
        if (optionalOwner.isPresent()) {
            ownerRepository.deleteById(longId);
             ResponseEntity.ok().body(optionalOwner.get());
        }
        throw new EntityNotFoundException("Невозможно удалить Owner, т.к. в базе нет owner с id = "+ownerId);
    }

    @GetMapping
    @Operation(summary = "Показать всех усыновителей приюта")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> allOwners = ownerRepository.findAll();
        if (allOwners.size() > 0) {
            return ResponseEntity.ok(allOwners);
        }
        return ResponseEntity.notFound().build();
    }

}
