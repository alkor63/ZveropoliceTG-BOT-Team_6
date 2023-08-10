package com.ward_n6.Controllers;

import com.ward_n6.entity.reports.OwnerReport2;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.service.OwnerReport2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/report2")
    @Tag(name = "Список отчетов от усыновителей во время испытательного срока", description = "CRUD-операции с отчетами")
    public class OwnerReport2Controller {
        private final OwnerReport2Service ownerReport2Service;

        public OwnerReport2Controller(OwnerReport2Service ownerReport2Service) {
            this.ownerReport2Service = ownerReport2Service;
        }

        @PostMapping
        @Operation(summary = "Добавление отчета в список", description = "нужно заполнить все поля отчета в Body")
        public ResponseEntity<OwnerReport2> createOwnerReport2(@RequestBody OwnerReport2 ownerReport2) {
            try {
                OwnerReport2 newOwnerReport2 = ownerReport2Service.addOwnerReport2(ownerReport2);
                return ResponseEntity.ok(newOwnerReport2);
            } catch (PutToMapException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }

        @GetMapping("/{ownerReport2Id}")
        @Operation(summary = "Показать один отчет по id", description = "нужно указать id отчёта")
        public ResponseEntity<OwnerReport2> getOwnerReport2(@PathVariable int ownerReport2Id) {
            OwnerReport2 ownerReport2 = ownerReport2Service.getOwnerReport2ById(ownerReport2Id);
            if (ownerReport2 == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ownerReport2);
        }

        @PutMapping("/{ownerReport2Id}")
        @Operation(summary = "Отредактировать отчёт",
                description = "нужно указать id и заполнить все поля отчёта в Body")
        public ResponseEntity<OwnerReport2> editOwnerReport2(@PathVariable int ownerReport2Id, @RequestBody OwnerReport2 ownerReport2) {
            try {
                OwnerReport2 newOwnerReport2 = ownerReport2Service.editOwnerReport2ById(ownerReport2Id, ownerReport2);
                return ResponseEntity.ok(newOwnerReport2);
            } catch (EditMapException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{ownerReport2Id}")
        @Operation(summary = "Удалить один отчёт из списка", description = "нужно указать id отчёта")
        public ResponseEntity<Void> deleteOwnerReport2(@PathVariable int ownerReport2Id) {
            try {
                ownerReport2Service.deleteOwnerReport2ById(ownerReport2Id);
                return ResponseEntity.ok().build();
            } catch (DeleteFromMapException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping
        @Operation(summary = "Удалить из списка все отчёты")
        public ResponseEntity<Void> deleteAllOwnerReport2s() {
            ownerReport2Service.deleteAllFromOwnerReport2();
            return ResponseEntity.ok().build();
        }

        @GetMapping
        @Operation(summary = "Показать все отчёты о животных")
        public ResponseEntity<List<OwnerReport2>> getAllOwnerReport2s() {
            List<OwnerReport2> allOwnerReport2s = ownerReport2Service.getAllOwnerReport2s();
            if (allOwnerReport2s.size() > 0) {
                return ResponseEntity.ok(allOwnerReport2s);
            }
            return ResponseEntity.notFound().build();
        }
}
