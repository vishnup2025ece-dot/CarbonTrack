package com.carbontrack;

import com.carbontrack.model.Challenge;
import com.carbontrack.repository.ChallengeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarbonTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonTrackApplication.class, args);
    }

    @Bean
    CommandLineRunner seedChallenges(ChallengeRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Challenge(null, "Switch off unused lights", "Save electricity by turning off lights when not needed.", 10, false));
                repository.save(new Challenge(null, "Use public transport", "Replace one private vehicle trip with public transport.", 20, false));
                repository.save(new Challenge(null, "Reduce food waste", "Plan meals and avoid throwing away edible food.", 15, false));
                repository.save(new Challenge(null, "Carry a reusable bottle", "Avoid single-use plastic bottles for one day.", 10, false));
                repository.save(new Challenge(null, "Segregate household waste", "Separate recyclable and organic waste correctly.", 15, false));
            }
        };
    }
}
