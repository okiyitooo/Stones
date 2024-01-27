package com.SecondPersonalProject.Stones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SecondPersonalProject.Stones.exceptions.EmptyPageException;
import com.SecondPersonalProject.Stones.exceptions.StoneCreationException;
import com.SecondPersonalProject.Stones.models.Stone;
import com.SecondPersonalProject.Stones.servicesAndImpls.StoneService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stones")
public class StoneController {

    @Autowired
    private StoneService stoneService;

    @PostMapping("/add")
    public ResponseEntity<Stone> createStone(@Valid @RequestBody Stone stoneDTO) {
        try {
            Stone createdStone = stoneService.createStone(stoneDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStone);
        } catch (StoneCreationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 20) Pageable pageable) {
        try {
            Page<Stone> stonesPage = stoneService.getAllStones(pageable);
            return ResponseEntity.ok(stonesPage.getContent());
        } catch (EmptyPageException e) {
            return ResponseEntity.noContent().build(); // Return 204 No Content for empty results
        }
    }
    
}
