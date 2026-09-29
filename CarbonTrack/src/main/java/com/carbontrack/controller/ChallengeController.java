package com.carbontrack.controller;

import com.carbontrack.model.Challenge;
import com.carbontrack.repository.ChallengeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin
public class ChallengeController {

    private final ChallengeRepository repository;

    public ChallengeController(ChallengeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Challenge> getAll() {
        return repository.findAll();
    }

    @GetMapping("/completed")
    public List<Challenge> getCompleted() {
        return repository.findByCompleted(true);
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<Challenge> toggle(@PathVariable Long id) {
        return repository.findById(id).map(challenge -> {
            challenge.setCompleted(!challenge.isCompleted());
            return ResponseEntity.ok(repository.save(challenge));
        }).orElse(ResponseEntity.notFound().build());
    }
}
