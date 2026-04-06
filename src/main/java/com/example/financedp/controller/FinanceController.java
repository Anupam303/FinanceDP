package com.example.financedp.controller;

import com.example.financedp.model.FinancialData;
import com.example.financedp.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class FinanceController {

    private FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialData>> getAll(){
        return ResponseEntity.ok(financeService.getAll());
    }

    @PostMapping("/{id}")
    public ResponseEntity<FinancialData> create(@PathVariable Long id,@Valid @RequestBody FinancialData financialData){
        return new ResponseEntity<>(financeService.create(id, financialData), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialData> update(@PathVariable Long id, @Valid @RequestBody FinancialData financialData){
        return new ResponseEntity<>(financeService.update(id, financialData), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        financeService.delete(id);
        return ResponseEntity.ok("Data deleted");
    }
}
