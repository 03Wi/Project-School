package com.project.school.controller;

import com.project.school.dto.RegistrationDto;
import com.project.school.model.Registration;
import com.project.school.service.IRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final IRegistrationService service;

    @Qualifier("mapperDefault")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<RegistrationDto>> readAll(){

        List<Registration> list = service.findAll();
        List<RegistrationDto> listDto = list.stream()
                .map(this::convertToDto)
                .toList();
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> readById(@PathVariable("id") Integer id) {

        Registration registration = service.findById(id);
        return new ResponseEntity<>(convertToDto(registration), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<RegistrationDto> save( @Valid @RequestBody RegistrationDto registration) {

        Registration param = service.save(convertToRegistration(registration));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    public ResponseEntity<RegistrationDto> update(@Valid @PathVariable("id") RegistrationDto registration, Integer id) {

        Registration param = service.save(convertToRegistration(registration));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    @GetMapping("/course/student")
    public ResponseEntity<Map<String, List<String>>> getCourseAndStudents() {

        Map<String, List<String>> courseAndStudents = service.courseAndStudent();
        return new ResponseEntity<>(courseAndStudents, HttpStatus.OK);
    }

    private RegistrationDto convertToDto(Registration registration){
        return mapper.map(registration, RegistrationDto.class);
    }
    private Registration convertToRegistration(RegistrationDto registrationDto){
        return mapper.map(registrationDto, Registration.class);
    }
}

