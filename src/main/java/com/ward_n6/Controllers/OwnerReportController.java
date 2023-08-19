package com.ward_n6.Controllers;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerReportRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
    public ResponseEntity<OwnerReport> addOwnerReport(@RequestBody @Valid OwnerReport ownerReport) {
        OwnerReport orp = ownerReportRepository.save(ownerReport);
        return ResponseEntity.status(200).body(orp);
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports() {
        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        if (allOwnerReports.size() > 0) {
            return ResponseEntity.ok().body(allOwnerReports);
        }
        return ResponseEntity.notFound().build();
    }
// ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> getOwnerReportById(@PathVariable Integer ownerReportId)
    throws EntityNotFoundException {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
        if(optionalOwnerReport.isPresent())
                    return ResponseEntity.ok().body(optionalOwnerReport.get());
        throw new EntityNotFoundException("В базе нет отчёта с id="+ownerReportId);
    }

//+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> deleteOwnerReportById(@PathVariable Integer ownerReportId)
    throws EntityNotFoundException {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
        if (optionalOwnerReport.isPresent()) {
            ownerReportRepository.deleteById(longId);
            return ResponseEntity.ok().body(optionalOwnerReport.get());
        }
throw new EntityNotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = "+ownerReportId);    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> editOwnerReportById(@PathVariable int ownerReportId,
                                                           @RequestBody @Valid OwnerReport ownerReport)
            throws EntityNotFoundException    {
        long longId = ownerReportId;
        Optional optionalOwnerReport = ownerReportRepository.findById(longId);
        if (!optionalOwnerReport.isPresent()) {
            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = "+ownerReportId);
        }
        OwnerReport existingOwnerReport = (OwnerReport) optionalOwnerReport.get();

        existingOwnerReport.setHavePhoto(ownerReport.isHavePhoto());
        existingOwnerReport.setNutrition(ownerReport.getNutrition());
        existingOwnerReport.setPetsBehavior(ownerReport.getPetsBehavior());
        existingOwnerReport.setPetsHealth(ownerReport.getPetsHealth());
        existingOwnerReport.setReportDateTime(ownerReport.getReportDateTime());

        return ResponseEntity.ok().body(ownerReportRepository.save(ownerReport));
    }
}
