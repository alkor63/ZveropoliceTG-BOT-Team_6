package com.ward_n6.Controllers;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.service.OwnerReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/report")
    @Tag(name = "Список отчетов от усыновителей во время испытательного срока", description = "CRUD-операции с отчетами")
    public class OwnerReportController {
        private final OwnerReportService ownerReportService;

        public OwnerReportController(OwnerReportService ownerReportService) {
            this.ownerReportService = ownerReportService;
        }

        @PostMapping
        @Operation(summary = "Добавление отчета в список", description = "нужно заполнить все поля отчета в Body")
        public ResponseEntity<OwnerReport> createOwnerReport(@RequestBody OwnerReport ownerReport) {
            try {
                OwnerReport newOwnerReport = ownerReportService.addOwnerReport(ownerReport);
                return ResponseEntity.ok(newOwnerReport);
            } catch (PutToMapException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        @GetMapping("/{ownerReportId}")
        @Operation(summary = "Показать один отчет по id", description = "нужно указать id отчёта")
        public ResponseEntity<OwnerReport> getOwnerReport(@PathVariable int ownerReportId) {
            OwnerReport ownerReport = ownerReportService.getOwnerReportById(ownerReportId);
            if (ownerReport == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ownerReport);
        }

        @PutMapping("/{ownerReportId}")
        @Operation(summary = "Отредактировать отчёт",
                description = "нужно указать id и заполнить все поля отчёта в Body")
        public ResponseEntity<OwnerReport> editOwnerReport(@PathVariable int ownerReportId, @RequestBody OwnerReport ownerReport) {
            try {
                OwnerReport newOwnerReport = ownerReportService.editOwnerReportById(ownerReportId, ownerReport);
                return ResponseEntity.ok(newOwnerReport);
            } catch (EditMapException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{ownerReportId}")
        @Operation(summary = "Удалить один отчёт из списка", description = "нужно указать id отчёта")
        public ResponseEntity<Void> deleteOwnerReport(@PathVariable int ownerReportId) {
            try {
                ownerReportService.deleteOwnerReportById(ownerReportId);
                return ResponseEntity.ok().build();
            } catch (DeleteFromMapException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping
        @Operation(summary = "Удалить из списка все отчёты")
        public ResponseEntity<Void> deleteAllOwnerReports() {
            ownerReportService.deleteAllFromOwnerReport();
            return ResponseEntity.ok().build();
        }

        @GetMapping
        @Operation(summary = "Показать все отчёты о животных")
        public ResponseEntity<List<OwnerReport>> getAllOwnerReports() {
            List<OwnerReport> allOwnerReports = ownerReportService.getAllOwnerReports();
            if (allOwnerReports.size() > 0) {
                return ResponseEntity.ok(allOwnerReports);
            }
            return ResponseEntity.notFound().build();
        }
}
