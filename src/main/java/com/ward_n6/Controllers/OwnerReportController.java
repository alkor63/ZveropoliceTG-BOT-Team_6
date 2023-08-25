package com.ward_n6.Controllers;


import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.owner.OwnerReportRepository;
import com.ward_n6.service.OwnerReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/report")
public class OwnerReportController {

    //    private final OwnerReportServiceImpl ownerReportService;
//
//    public OwnerReportController(OwnerReportServiceImpl ownerReportService) {
//        this.ownerReportService = ownerReportService;
//    }
    @Autowired
    private OwnerReportServiceImpl ownerReportServiceImpl;

    @Autowired
    private OwnerReportRepository ownerReportRepository;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @PostMapping
    @Operation(summary = "Добавление отчёта в список",
            description = "нужно заполнить все поля отчёта в Body")
    public ResponseEntity<OwnerReport> addOwnerReport(@RequestBody @Valid OwnerReport ownerReport)
    {
        OwnerReport newOwnerReport = ownerReportRepository.save(ownerReport);
        return new ResponseEntity<>(newOwnerReport, HttpStatus.CREATED);
    }
// ++++++++++++++++++++++++++++++++++++

    @GetMapping
    @Operation(summary = "Показать все отчёты")
    public ResponseEntity<List<OwnerReport>> getAllOwnerReports()
    {
        return ResponseEntity.ok().body(ownerReportRepository.findAll());
    }

    // ++++++++++++++++++++++++++++++++++++
    @GetMapping("/{ownerReportId}")
    @Operation(summary = "Показать один отчёт по id",
            description = "нужно указать id отчёта")
    public ResponseEntity<OwnerReport>  getOwnerReportById(@PathVariable Long ownerReportId)
    {
        return ResponseEntity.ok().body(ownerReportRepository.getById(ownerReportId));
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @DeleteMapping("/{ownerReportId}")
    @Operation(summary = "Удалить один отчёт из списка",
            description = "нужно указать id отчёта")
    public ResponseEntity<String> deleteOwnerReportById(@PathVariable Integer ownerReportId)
    {
        boolean deleteOwnerReportById = ownerReportServiceImpl.deleteOwnerReportById(ownerReportId);
        if (deleteOwnerReportById) {
            return new ResponseEntity<>(("OwnerReport id = " + ownerReportId + "успешно удален из базы"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(("Ошибка при попытке удалить запись OwnerReport ID = " + ownerReportId), HttpStatus.BAD_REQUEST);
        }
//    public ResponseEntity<String> deleteOwnerReportById(@PathVariable Long id) {

    }

    @PutMapping("/{ownerReportId}")
    @Operation(summary = "Отредактировать отчёт",
            description = "нужно указать id и заполнить все поля отчёта в Body")
    public OwnerReport editOwnerReportById(@PathVariable int ownerReportId,
                                           @RequestBody @Valid OwnerReport ownerReport)
    {
        return ownerReportServiceImpl.editOwnerReportById(ownerReportId, ownerReport);
    }
}
