package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.RegistrationDto;
import com.project.school.model.Registration;
import com.project.school.service.IRegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school/registration")
@RequiredArgsConstructor
@Tag(name = "Registration", description = SwaggerConfig.description)
public class RegistrationController {

    private final IRegistrationService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<RegistrationDto>> readAll(Pageable pageable){

        Page<Registration> page = service.findAll(pageable);
        Page<RegistrationDto> registrationDtoPage = page.map(this::convertToDto);
        return new ResponseEntity<>(registrationDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> readById(@PathVariable("id") Integer id) {

        Registration registration = service.findById(id);
        return new ResponseEntity<>(convertToDto(registration), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<RegistrationDto> save( @Valid @RequestBody RegistrationDto registration) {

        Registration param = service.save(convertToRegistration(registration));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<RegistrationDto> update(@Valid @RequestBody RegistrationDto registration, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Registration param = service.update(convertToRegistration(registration), id);
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

