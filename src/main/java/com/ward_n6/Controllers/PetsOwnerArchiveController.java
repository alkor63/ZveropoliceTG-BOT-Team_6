package com.ward_n6.Controllers;

import com.ward_n6.entity.owners.PetsOwnerArchive;
import com.ward_n6.repository.PetsOwnerArchiveRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pets_owner_archive")
public class PetsOwnerArchiveController {

    private final PetsOwnerArchiveRepository petsOwnerArchiveRepository;

    public PetsOwnerArchiveController(PetsOwnerArchiveRepository petsOwnerArchiveRepository) {
        this.petsOwnerArchiveRepository = petsOwnerArchiveRepository;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление записи в список",
            description = "нужно заполнить все поля записи в Body")
    public ResponseEntity<PetsOwnerArchive> addPetsOwnerArchive(@RequestBody @Valid PetsOwnerArchive petsOwnerArchive) {
        PetsOwnerArchive poa = (PetsOwnerArchive) petsOwnerArchiveRepository.save(petsOwnerArchive);
        return ResponseEntity.status(200).body(poa);
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<PetsOwnerArchive>> getAllPetsOwnerArchives() {
        List<PetsOwnerArchive> allPetsOwnerArchives = petsOwnerArchiveRepository.findAll();
        if (allPetsOwnerArchives.size() > 0) {
            return ResponseEntity.ok().body(allPetsOwnerArchives);
        }
        return ResponseEntity.notFound().build();
    }
    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{petsOwnerArchiveId}")
    @Operation(summary = "Показать один запись по id",
            description = "нужно указать id ")
    public ResponseEntity<PetsOwnerArchive> getPetsOwnerArchiveById(@PathVariable Integer petsOwnerArchiveId)
            throws EntityNotFoundException {
        long longId = petsOwnerArchiveId;
        Optional<PetsOwnerArchive> optionalPetsOwnerArchive = petsOwnerArchiveRepository.findById(longId);
        if(optionalPetsOwnerArchive.isPresent())
            return ResponseEntity.ok().body(optionalPetsOwnerArchive.get());
        throw new EntityNotFoundException("В базе нет отчёта с id ="+petsOwnerArchiveId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{petsOwnerArchiveId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<PetsOwnerArchive> deletePetsOwnerArchiveById(@PathVariable Integer petsOwnerArchiveId)
            throws EntityNotFoundException {
        long longId = petsOwnerArchiveId;
        Optional<PetsOwnerArchive> optionalPetsOwnerArchive = petsOwnerArchiveRepository.findById(longId);
        if (optionalPetsOwnerArchive.isPresent()) {
            petsOwnerArchiveRepository.deleteById(longId);
            return ResponseEntity.ok().body(optionalPetsOwnerArchive.get());
        }
        throw new EntityNotFoundException("Невозможно удалить запись, т.к. в базе нет id = "+petsOwnerArchiveId);    }
// Здесь нет метода put/edit, т.к. это архив
}
