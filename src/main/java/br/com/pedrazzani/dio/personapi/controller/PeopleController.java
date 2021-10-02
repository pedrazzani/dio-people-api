package br.com.pedrazzani.dio.personapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/people")
public class PeopleController {

    @GetMapping
    ResponseEntity<String> getPeople() {
        return ResponseEntity.ok("List of People.");
    }
}
