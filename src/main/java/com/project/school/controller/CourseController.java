package com.project.school.controller;

import com.project.school.dto.CourseDto;
import com.project.school.model.Course;
import com.project.school.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school/course")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService service;

    @Qualifier("mapperDefault")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> readAll(){

        List<Course> list = service.findAll();
        List<CourseDto> listDto = list.stream()
                .map(this::convertToDto)
                .toList();
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> readById(@PathVariable("id") Integer id) {

        Course course = service.findById(id);
        return new ResponseEntity<>(convertToDto(course), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CourseDto> save( @Valid @RequestBody CourseDto course) {

        Course param = service.save(convertToCourse(course));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CourseDto> update(@Valid @PathVariable("id") CourseDto course, Integer id) {

        Course param = service.save(convertToCourse(course));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private CourseDto convertToDto(Course course){
        return mapper.map(course, CourseDto.class);
    }
    private Course convertToCourse(CourseDto courseDto){
        return mapper.map(courseDto, Course.class);
    }
}

