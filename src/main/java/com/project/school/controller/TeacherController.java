package com.project.school.controller;

import com.project.school.dto.TeacherDto;
import com.project.school.model.Teacher;
import com.project.school.service.ITeacherService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/school/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final ITeacherService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

//    @PreAuthorize("@authServiceImpl.hasAccess('/readAll')")
    @GetMapping("/all")
    public ResponseEntity<Page<TeacherDto>> readAll(Pageable pageable){

        Page<Teacher> page = service.findAll(pageable);
        Page<TeacherDto> pageDto = page.map(this::convertToDto);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> readById(@PathVariable("id") Integer id) {

        Teacher teacher = service.findById(id);
        return new ResponseEntity<>(convertToDto(teacher), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<TeacherDto> create( @Valid @RequestBody TeacherDto teacher) {

        Teacher param = service.save(convertToTeacher(teacher));
        TeacherDto teacherDto = convertToDto(param);
        return new ResponseEntity<>(teacherDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<TeacherDto> update(@Valid @RequestBody TeacherDto teacher, @PathVariable Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Teacher param = service.update(convertToTeacher(teacher), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private TeacherDto convertToDto(Teacher teacher){
        return mapper.map(teacher, TeacherDto.class);
    }
    private Teacher convertToTeacher(TeacherDto teacherDto){
        return mapper.map(teacherDto, Teacher.class);
    }
}

