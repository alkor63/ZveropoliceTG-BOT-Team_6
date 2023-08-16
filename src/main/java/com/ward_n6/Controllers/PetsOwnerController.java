package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.PetsOwnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(value = "/pets_owner")
public class PetsOwnerController {

        private final PetsOwnerRepository petsOwnerRepository;

        public PetsOwnerController(PetsOwnerRepository petsOwnerRepository) {
            this.petsOwnerRepository = petsOwnerRepository;
        }

        //++++++++++++++++++++++++++++++++++++++++++++++++++++
        @PostMapping
        @Operation(summary = "Добавление записи в список",
                description = "нужно заполнить все поля записи в Body")
        public ResponseEntity<PetsOwner> addPetsOwner(@RequestBody @Valid PetsOwner petsOwner) {
            PetsOwner orp = petsOwnerRepository.save(petsOwner);
            return ResponseEntity.status(200).body(orp);
        }
// ++++++++++++++++++++++++++++++++++++

        @GetMapping
        @Operation(summary = "Показать все отчёты")
        public ResponseEntity<List<PetsOwner>> getAllPetsOwners() {
            List<PetsOwner> allPetsOwners = petsOwnerRepository.findAll();
            if (allPetsOwners.size() > 0) {
                return ResponseEntity.ok().body(allPetsOwners);
            }
            return ResponseEntity.notFound().build();
        }
        // ++++++++++++++++++++++++++++++++++++
        @GetMapping("/{petsOwnerId}")
        @Operation(summary = "Показать один запись по id",
                description = "нужно указать id ")
        public ResponseEntity<PetsOwner> getPetsOwnerById(@PathVariable Integer petsOwnerId)
                throws EntityNotFoundException {
            long longId = petsOwnerId;
            Optional<PetsOwner> optionalPetsOwner = petsOwnerRepository.findById(longId);
            if(optionalPetsOwner.isPresent())
                return ResponseEntity.ok().body(optionalPetsOwner.get());
            throw new EntityNotFoundException("В базе нет отчёта с id="+petsOwnerId);
        }

        //+++++++++++++++++++++++++++++++++++++++++
        @DeleteMapping("/{petsOwnerId}")
        @Operation(summary = "Удалить один отчёт из списка",
                description = "нужно указать id отчёта")
        public ResponseEntity<PetsOwner> deletePetsOwnerById(@PathVariable Integer petsOwnerId)
                throws EntityNotFoundException {
            long longId = petsOwnerId;
            Optional<PetsOwner> optionalPetsOwner = petsOwnerRepository.findById(longId);
            if (optionalPetsOwner.isPresent()) {
                petsOwnerRepository.deleteById(longId);
                return ResponseEntity.ok().body(optionalPetsOwner.get());
            }
            throw new EntityNotFoundException("Невозможно удалить запись, т.к. в базе нет id = "+petsOwnerId);    }

        @PutMapping("/{petsOwnerId}")
        @Operation(summary = "Отредактировать отчёт",
                description = "нужно указать id и заполнить все поля  Body")
        public ResponseEntity<PetsOwner> editPetsOwnerById(@PathVariable int petsOwnerId,
                                                               @RequestBody @Valid PetsOwner petsOwner)
                throws EntityNotFoundException    {
            long longId = petsOwnerId;
            Optional optionalPetsOwner = petsOwnerRepository.findById(longId);
            if (!optionalPetsOwner.isPresent()) {
                throw new EntityNotFoundException("Невозможно изменить запись, т.к. в базе нет id = "+petsOwnerId);
            }
            PetsOwner existingPetsOwner = (PetsOwner) optionalPetsOwner.get();

            existingPetsOwner.setDateEnd(petsOwner.getDateEnd());
            existingPetsOwner.setDateBegin(petsOwner.getDateBegin());
            existingPetsOwner.setOwner(petsOwner.getOwner());
            existingPetsOwner.setPet(petsOwner.getPet());

            return ResponseEntity.ok().body(petsOwnerRepository.save(petsOwner));
        }
}
