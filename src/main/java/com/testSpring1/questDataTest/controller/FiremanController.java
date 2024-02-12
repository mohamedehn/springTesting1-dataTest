package com.testSpring1.questDataTest.controller;


import com.testSpring1.questDataTest.DTO.FiremanStatsDTO;
import com.testSpring1.questDataTest.entity.Fireman;
import com.testSpring1.questDataTest.repository.FiremanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/fireman")
public class FiremanController {

    @Autowired
    FiremanRepository firemanRepository;

    record FiremanData(Long id, String name, int firesCount) {
        static FiremanData fromFireman(Fireman fireman) {
            return new FiremanData(fireman.getId(), fireman.getName(), fireman.getFires().size());
        }
    }

    @GetMapping("/veteran")
    public FiremanData getVeteran() {
        Optional<Fireman> veteranMaybe = firemanRepository.getVeteran();
        Fireman veteran = veteranMaybe.orElseThrow(() -> new NotFoundException());
        return FiremanData.fromFireman(veteran);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }

    @GetMapping("/getStats")
    public FiremanData getStats() {
        Optional<Fireman> firemanStatsDTO = firemanRepository.getAll();
        return new FiremanData(firemanStatsDTO.get().getId(), firemanStatsDTO.get().getName(), firemanStatsDTO.get().getFires().size());
    }
}

