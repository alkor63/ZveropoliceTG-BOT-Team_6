package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.repository.OwnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление список",
            description = "нужно заполнить все поля Body")
    public ResponseEntity<Owner> addOwner(@RequestBody @Valid Owner owner) {
        Owner orp = ownerRepository.save(owner);
        return ResponseEntity.status(200).body(orp);
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать всех")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> allOwners = ownerRepository.findAll();
        if (allOwners.size() > 0) {
            return ResponseEntity.ok().body(allOwners);
        }
        return ResponseEntity.notFound().build();
    }
    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerId}")
    @Operation(summary = "Показать один по id",
            description = "нужно указать id")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Integer ownerId)
            throws EntityNotFoundException {
        long longId = ownerId;
        Optional<Owner> optionalOwner = ownerRepository.findById(longId);
        if(optionalOwner.isPresent())
            return ResponseEntity.ok().body(optionalOwner.get());
        throw new EntityNotFoundException("В базе нет отчёта с id="+ownerId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerId}")
    @Operation(summary = "Удалить одну запись из списка",
            description = "нужно указать id")
    public ResponseEntity<Owner> deleteOwnerById(@PathVariable Integer ownerId)
            throws EntityNotFoundException {
        long longId = ownerId;
        Optional<Owner> optionalOwner = ownerRepository.findById(longId);
        if (optionalOwner.isPresent()) {
            ownerRepository.deleteById(longId);
            return ResponseEntity.ok().body(optionalOwner.get());
        }
        throw new EntityNotFoundException("Невозможно удалить запись, т.к. в базе нет id = "+ownerId);    }

    @PutMapping("/{ownerId}")
    @Operation(summary = "Отредактировать запись",
            description = "нужно указать id и заполнить все поля в Body")
    public ResponseEntity<Owner> editOwnerById(@PathVariable int ownerId,
                                                           @RequestBody @Valid Owner owner)
            throws EntityNotFoundException    {
        long longId = ownerId;
        Optional optionalOwner = ownerRepository.findById(longId);
        if (!optionalOwner.isPresent()) {
            throw new EntityNotFoundException("Невозможно изменить запись, т.к. в базе нет записи с id = "+ownerId);
        }
        Owner existingOwner = (Owner) optionalOwner.get();

        existingOwner.setFirstName(owner.getFirstName());
        existingOwner.setLastName(owner.getLastName());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());

        return ResponseEntity.ok().body(ownerRepository.save(owner));
    }
}

