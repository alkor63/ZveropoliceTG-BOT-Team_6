package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.interfaces.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/owner")
@Tag(name = "Список посетителей приюта",
        description = "CRUD-операции с усыновителями")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Operation(summary = "Добавление посетителя в список",
            description = "нужно заполнить все поля карточки посетителя в Body, телефон указать в формате 8-ХХХ-ХХХ-ХХ-ХХ. \n" +
                    "В качестве ID укажите номер телефона без дефисов.")
    @PostMapping("/createOwner")
    public ResponseEntity<Owner> createOwner(@RequestBody @Valid Owner owner) {

        Owner newOwner = ownerService.createOwner(owner);
        return ResponseEntity.ok(newOwner);
    }

    @Operation(summary = "Показать одного усыновителя по id",
            description = "нужно указать id усыновителя")
    @GetMapping("/getOwner/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable long id) {
        return ResponseEntity.ok().body(ownerService.findOwnerById(id));
    }


    @Operation(summary = "Отредактировать карточку усыновителя",
            description = "нужно указать id и заполнить все поля карточки усыновителя в Body")
    @PutMapping("/editOwner/{id}")
    public Owner editOwnerById(@PathVariable long id,
                               @RequestBody @Valid Owner owner) {
        return ownerService.editOwnerById(id, owner);
    }


    @Operation(summary = "Удалить одного усыновителя из списка",
            description = "нужно указать id усыновителя")
    @DeleteMapping("/deleteOwner/{id}")
    public ResponseEntity<String> deleteOwnerById(@PathVariable long id) {
        boolean deleteOwnerById = ownerService.deleteOwnerById(id);
        if (deleteOwnerById) {
            return new ResponseEntity<>(("Owner id = " + id + " удален"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(("Ошибка ID = " + id), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Показать всех усыновителей приюта")
    @GetMapping("/getAllOwners")
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok().body(ownerService.getAllOwners());
    }

}