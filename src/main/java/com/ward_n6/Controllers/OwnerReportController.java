package com.ward_n6.Controllers;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.service.OwnerReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/report")
public class OwnerReportController {
    //    @Resource
    private final OwnerReportService ownerReportService;

    public OwnerReportController(OwnerReportService ownerReportService) {
        this.ownerReportService = ownerReportService;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление отчёта в список",
            description = "нужно заполнить все поля отчёта в Body")
    public OwnerReport addOwnerReport(@RequestBody @Valid OwnerReport ownerReport)
    {
        return ownerReportService.addOwnerReport(ownerReport);
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports()
    {
        return (ResponseEntity<List<OwnerReport>>) ownerReportService.getAllOwnerReports();
    }

    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public OwnerReport getOwnerReportById(@PathVariable Integer ownerReportId)
             {
        return ownerReportService.getOwnerReportById(ownerReportId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport> deleteOwnerReportById(@PathVariable Integer ownerReportId)
    {
        return ownerReportService.deleteOwnerReportById(ownerReportId);
    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public OwnerReport editOwnerReportById(@PathVariable int ownerReportId,
                                           @RequestBody @Valid OwnerReport ownerReport)
    {
        return ownerReportService.editOwnerReportById(ownerReportId, ownerReport);
    }
}
