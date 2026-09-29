package com.carbontrack.controller;

import com.carbontrack.dto.CalculationRequest;
import com.carbontrack.model.CarbonCalculation;
import com.carbontrack.repository.CarbonCalculationRepository;
import com.carbontrack.service.CarbonCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calculations")
@CrossOrigin
public class CarbonCalculationController {

    private final CarbonCalculationRepository repository;
    private final CarbonCalculatorService calculator;

    public CarbonCalculationController(CarbonCalculationRepository repository,
                                       CarbonCalculatorService calculator) {
        this.repository = repository;
        this.calculator = calculator;
    }

    @PostMapping
    public CarbonCalculation create(@RequestBody CalculationRequest request) {
        return repository.save(calculator.calculate(request));
    }

    @GetMapping
    public List<CarbonCalculation> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarbonCalculation> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
