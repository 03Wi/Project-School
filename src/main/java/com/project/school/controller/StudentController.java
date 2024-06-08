package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.StudentDto;
import com.project.school.model.Student;
import com.project.school.service.IStudentService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/school/student")
@RequiredArgsConstructor
@Tag(name = "Student", description = SwaggerConfig.description)
public class StudentController {

    private final IStudentService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

//    @PreAuthorize("@authServiceImpl.hasAccess('/readAll')")
    @GetMapping("/all")
    public ResponseEntity<Page<StudentDto>> readAll(Pageable pageable){

        Page<Student> page = service.findAll(pageable);
        Page<StudentDto> pageDto = page.map(this::convertToDto);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> readById(@PathVariable("id") Integer id) {

        Student student = service.findById(id);
        return new ResponseEntity<>(convertToDto(student), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<StudentDto> create( @Valid @RequestBody StudentDto student) {

        Student param = service.save(convertToStudent(student));
        StudentDto studentDto = convertToDto(param);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentDto> update(@Valid @RequestBody StudentDto student, @PathVariable Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Student param = service.update(convertToStudent(student), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    @GetMapping("/age/desc")
    public ResponseEntity<List<StudentDto>> studentByOrderAgeDesc(Pageable pageable) {

        List<StudentDto>  list = service.findAllByOrderAgeDesc(pageable).stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    private StudentDto convertToDto(Student student){
        return mapper.map(student, StudentDto.class);
    }
    private Student convertToStudent(StudentDto studentDto){
        return mapper.map(studentDto, Student.class);
    }
}

