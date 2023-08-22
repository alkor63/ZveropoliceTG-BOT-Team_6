package com.ward_n6.Controllers;


import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.InvalidRequestException;
import com.ward_n6.repository.ReportRepository;
import com.ward_n6.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/report")
public class OwnerReportController {
    //    @Resource
    private final ReportRepository reportRepository;
    public final ReportService reportService;

    public OwnerReportController(ReportRepository reportRepository, ReportService reportService) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление отчёта в список",
            description = "нужно заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> addOwnerReport(@RequestBody OwnerReport ownerReport) {
        reportRepository.save(ownerReport);
        return ResponseEntity.ok(ownerReport);
    }

    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> getOwnerReportById(@PathVariable Integer ownerReportId) {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = reportRepository.findById(longId);
        if (optionalOwnerReport.isPresent()) {
            final OwnerReport ownerReport = optionalOwnerReport.get();
            return ResponseEntity.ok(ownerReport);
        }
        return ResponseEntity.notFound().build();
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<Void> deleteOwnerReportById(@PathVariable Integer ownerReportId) {
        long longId = ownerReportId;
        if (reportRepository.findById(longId).isPresent()) {
            reportRepository.deleteById(longId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports() {
        List<OwnerReport> allOwnerReports = reportRepository.findAll();
        if (allOwnerReports.size() > 0) {
            return ResponseEntity.ok(allOwnerReports);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> editOwnerReportById(@PathVariable int ownerReportId, @RequestBody OwnerReport ownerReport) {
        if (ownerReport == null) {
            //удалено: || ownerReport.getId() == null
            throw new InvalidRequestException("PatientRecord or ID must not be null!");
        }
        Optional<OwnerReport> optionalOwnerReport = reportRepository.findById(ownerReport.getOwnerId());
        if (!optionalOwnerReport.isPresent()) {
            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
        }
        reportService.optiOwnerReport(ownerReport);
        reportRepository.save(ownerReport);
        return ResponseEntity.ok(ownerReport);
    }
}
