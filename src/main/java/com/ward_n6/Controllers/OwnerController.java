package com.ward_n6.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;
import com.ward_n6.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner")
@Tag(name = "Список посетителей приюта",
        description = "CRUD-операции с усыновителями")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    @Operation(summary = "Добавление посетителя в список",
            description = "нужно заполнить все поля карточки посетителя в Body")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) throws JsonProcessingException, PutToMapException {
        Owner newOwner = ownerService.addOwner(owner);
        return ResponseEntity.ok(newOwner);
    }

    @GetMapping("/{ownerId}")
    @Operation(summary = "Показать одного усыновителя по id",
            description = "нужно указать id усыновителя")
    public ResponseEntity<Owner> getOwner(@PathVariable int ownerId) {
        Owner owner = ownerService.getOwnerById(ownerId);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    @PutMapping("/{ownerId}")
    @Operation(summary = "Отредактировать карточку усыновителя",
            description = "нужно указать id и заполнить все поля карточки усыновителя в Body")
    public ResponseEntity<Owner> editOwner(@PathVariable int ownerId, @RequestBody Owner owner) throws EditMapException {
        Owner newOwner = ownerService.editOwnerById(ownerId, owner);
        if (newOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newOwner);
    }

    @DeleteMapping("/{ownerId}")
    @Operation(summary = "Удалить одного усыновителя из списка",
            description = "нужно указать id усыновителя")
    public ResponseEntity<Void> deleteOwner(@PathVariable int ownerId) throws DeleteFromMapException {
        if (ownerService.deleteOwnerById(ownerId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Operation(summary = "Удалить из списка всех усыновителей - приют закрывается")
    public ResponseEntity<Void> deleteAllOwners() {
        ownerService.deleteAllFromOwner();
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "Показать всех усыновителей приюта")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> allOwners = ownerService.getAllOwners();
        if (allOwners.size() > 0) {
            return ResponseEntity.ok(allOwners);
        }
        return ResponseEntity.notFound().build();
    }

}
