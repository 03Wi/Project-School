package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.CourseDto;
import com.project.school.model.Course;
import com.project.school.service.ICourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/school/course")
@RequiredArgsConstructor
@Tag(name = "Course", description = SwaggerConfig.description)
public class CourseController {

    private final ICourseService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<CourseDto>> readAll(Pageable pageable){

        Page<Course> page = service.findAll(pageable);
        Page<CourseDto> courseDtos = page.map(this::convertToDto);
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> readById(@PathVariable("id") Integer id) {

        Course course = service.findById(id);
        return new ResponseEntity<>(convertToDto(course), HttpStatus.OK);
    }


    @PostMapping("/save")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<CourseDto> save( @Valid @RequestBody CourseDto courseDto) {

        Course param = service.save(convertToCourse(courseDto));
        return new ResponseEntity<>(convertToDto(param), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){

        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<CourseDto> update(@Valid @RequestBody CourseDto course, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Course param = service.update(convertToCourse(course), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private CourseDto convertToDto(Course course){
        return mapper.map(course, CourseDto.class);
    }
    private Course convertToCourse(CourseDto courseDto){
        return mapper.map(courseDto, Course.class);
    }
}

