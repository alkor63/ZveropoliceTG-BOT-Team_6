package com.ward_n6.Controllers;


import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.service.interfaces.OwnerReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Setter
@Getter
@RestController
@RequestMapping(value = "/report")
public class OwnerReportController {


    private final OwnerReportService ownerReportService;

    public OwnerReportController(OwnerReportService ownerReportService) {
        this.ownerReportService = ownerReportService;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление отчёта в список",
            description = "нужно заполнить все поля отчёта")
    public ResponseEntity<OwnerReport> addOwnerReport(long ownerId, PetsType petsType, boolean photo,
                                                      String nutrition, String health, String behavior, long petId) {

        OwnerReport newOwnerReport = ownerReportService.addOwnerReportFromController(ownerId, petsType, photo,
                nutrition, health, behavior, petId);
        return new ResponseEntity<>(newOwnerReport, HttpStatus.CREATED); // код 201
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports() {
        return ResponseEntity.ok().body(ownerReportService.getAllOwnerReports());
    }

    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> getOwnerReportById(@PathVariable Integer ownerReportId) {
        return ResponseEntity.ok().body(ownerReportService.getOwnerReportById(ownerReportId));
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<String> deleteOwnerReportById(@PathVariable long ownerReportId) {
        boolean deleteOwnerReportById = ownerReportService.deleteOwnerReportById(ownerReportId);
        if (deleteOwnerReportById) {
            return new ResponseEntity<>(("OwnerReport id = " + ownerReportId + "успешно удален из базы"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(("Ошибка при попытке удалить запись OwnerReport ID = " + ownerReportId), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public OwnerReport editOwnerReportById(@PathVariable long ownerReportId,
                                           @RequestParam boolean photo,
                                           String nutrition, String health, String behavior) {
        return ownerReportService.editOwnerReportByIdFromController(ownerReportId, photo, nutrition, health, behavior);
    }

}