package com.project.school.controller;

import com.project.school.dto.StudentDto;
import com.project.school.model.Student;
import com.project.school.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/school/student")
@RequiredArgsConstructor
public class StudentController {

    private final IStudentService service;

    @Qualifier("mapperDefault")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> readAll(){

        List<Student> list = service.findAll();
        List<StudentDto> listDto = list.stream()
                .map(this::convertToDto)
                .toList();
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> readById(@PathVariable("id") Integer id) {

        Student student = service.findById(id);
        return new ResponseEntity<>(convertToDto(student), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<StudentDto> create( @Valid @RequestBody StudentDto student) {

        Student param = service.save(convertToStudent(student));
        StudentDto studentDto = convertToDto(param);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{id}")
    public ResponseEntity<StudentDto> update(@Valid @PathVariable("id") StudentDto student, Integer id) {

        Student param = service.save(convertToStudent(student));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    @GetMapping("/age/desc")
    public ResponseEntity<List<StudentDto>> studentByOrderAgeDesc() {

        List<StudentDto>  list = service.findAllByOrderAgeDesc().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    private StudentDto convertToDto(Student student){
        return mapper.map(student, StudentDto.class);
    }
    private Student convertToStudent(StudentDto studentDto){
        return mapper.map(studentDto, Student.class);
    }
}

