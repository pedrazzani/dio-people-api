package br.com.pedrazzani.dio.personapi.controller;

import br.com.pedrazzani.dio.personapi.dto.request.PersonDto;
import br.com.pedrazzani.dio.personapi.dto.response.MessageResponseDto;
import br.com.pedrazzani.dio.personapi.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PersonService personService;

    @GetMapping
    ResponseEntity<List<PersonDto>> getAll() {
        return ResponseEntity.of(personService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<PersonDto> getById(@PathVariable Long id) {
        return ResponseEntity.of(personService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<PersonDto> create(@Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.of(personService.createPerson(personDto));
    }

    @PutMapping("/{id}")
    void update(@Valid @RequestBody PersonDto personDto, @PathVariable Long id) {
        personService.update(id, personDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        personService.delete(id);
    }

}
