package com.ward_n6.Controllers;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.CatsCrud;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.InvalidRequestException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReportRepository;
import io.swagger.v3.oas.annotations.Operation;
import javassist.NotFoundException;
import liquibase.pro.packaged.O;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/report")
public class OwnerReportController {
    //    @Resource
    private final OwnerReportRepository ownerReportRepository;

    public OwnerReportController(OwnerReportRepository ownerReportRepository) {
        this.ownerReportRepository = ownerReportRepository;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление отчёта в список",
            description = "нужно заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> addOwnerReport(@RequestBody OwnerReport ownerReport) {
        ownerReportRepository.save(ownerReport);
        return ResponseEntity.ok(ownerReport);
    }
// ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> getOwnerReportById(@PathVariable Integer ownerReportId) {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
        if(optionalOwnerReport.isPresent()) {
            final OwnerReport ownerReport = optionalOwnerReport.get();
        return ResponseEntity.ok(ownerReport);}
          return ResponseEntity.notFound().build();
    }

//+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<Void> deleteOwnerReportById(@PathVariable Integer ownerReportId) {
        long longId = ownerReportId;
        if (ownerReportRepository.findById(longId).isPresent()) {
            ownerReportRepository.deleteById(longId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports() {
        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        if (allOwnerReports.size() > 0) {
            return ResponseEntity.ok(allOwnerReports);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> editOwnerReportById(@PathVariable int ownerReportId, @RequestBody OwnerReport ownerReport) {
        if (ownerReport == null || ownerReport.getId() == null) {
            throw new InvalidRequestException("PatientRecord or ID must not be null!");
        }
        Optional optionalOwnerReport = ownerReportRepository.findById(ownerReport.getId());
        if (optionalOwnerReport.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        OwnerReport existingOwnerReport = (OwnerReport) optionalOwnerReport.get();

        existingOwnerReport.setHavePhoto(ownerReport.isHavePhoto());
        existingOwnerReport.setNutrition(ownerReport.getNutrition());
        existingOwnerReport.setPetsBehavior(ownerReport.getPetsBehavior());
        existingOwnerReport.setPetsHealth(ownerReport.getPetsHealth());
        existingOwnerReport.setReportDateTime(ownerReport.getReportDateTime());

        ownerReportRepository.save(ownerReport);
        return ResponseEntity.ok(ownerReport);
    }
}
