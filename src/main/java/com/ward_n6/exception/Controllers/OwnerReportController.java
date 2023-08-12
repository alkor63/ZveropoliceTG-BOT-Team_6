package com.ward_n6.exception.Controllers;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.CatsCrud;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReportRepository;
import io.swagger.v3.oas.annotations.Operation;
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

//    @PutMapping("/{ownerReportId}")
//    @Operation(summary = "Отредактировать отчёт",
//            description = "нужно указать id и заполнить все поля отчёта в Body")
//    public ResponseEntity<OwnerReport> editOwnerReportById(@PathVariable long ownerReportId, @RequestBody OwnerReport ownerReport) {
//
//        OwnerReport newOwnerReport = ownerReportRepository.updateReport(ownerReportId,
//                ownerReport.isHavePhoto(),
//                ownerReport.getNutrition(),
//                ownerReport.getPetsBehavior(),
//                ownerReport.getPetsHealth(),
//                ownerReport.getReportDateTime()
//                );
//        if (newOwnerReport == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(newOwnerReport);
//    }
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
//        UPDATE goods SET title = "утюг", price = 300 WHERE num = 2
//    OwnerReport owr;
// //   @Modifying
//    @Query("UPDATE owner_report  "WHERE owr.id = id");

//        @Query("update owr set owr.nutrition = :nutrition WHERE owr.id = :id");
//        @Query("update owr set owr.petsBehavior = :petsBehavior WHERE owr.id = :id");
//        @Query("update owr set owr.petsHealth = :petsHealth WHERE owr.id = :id");
//        @Query("update owr set owr.reportDateTime = : reportDateTime WHERE owr.id = :id");
//
//        OwnerReport updateReport(@Param("id") Long id,
//        @Param("havePhoto") boolean havePhoto,
//        @Param("nutrition") String nutrition,
//        @Param("petsBehavior") String petsBehavior,
//        @Param("petsHealth") String petsHealth,
//        @Param("reportDateTime") LocalDateTime reportDateTime
//    );
}
