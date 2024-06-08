package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.CourseScheduleDto;
import com.project.school.model.CourseSchedule;
import com.project.school.service.ICourseScheduleService;
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

@RestController
@RequestMapping("/school/courseSchedule")
@RequiredArgsConstructor
@Tag(name = "Course and Schedule", description = SwaggerConfig.description)
public class CourseScheduleController {

    private final ICourseScheduleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<CourseScheduleDto>> readAll(Pageable pageable){

        Page<CourseSchedule> page = service.findAll(pageable);
        Page<CourseScheduleDto> courseScheduleDtos = page.map(this::convertToDto);
        return new ResponseEntity<>(courseScheduleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseScheduleDto> readById(@PathVariable("id") Integer id) {

        CourseSchedule courseSchedule = service.findById(id);
        return new ResponseEntity<>(convertToDto(courseSchedule), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<CourseScheduleDto> save( @Valid @RequestBody CourseScheduleDto courseScheduleDto) {

        CourseSchedule param = service.save(convertToCourseSchedule(courseScheduleDto));
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
    public ResponseEntity<CourseScheduleDto> update(@Valid @RequestBody CourseScheduleDto courseSchedule, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        CourseSchedule param = service.update(convertToCourseSchedule(courseSchedule), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private CourseScheduleDto convertToDto(CourseSchedule courseSchedule){
        return mapper.map(courseSchedule, CourseScheduleDto.class);
    }
    private CourseSchedule convertToCourseSchedule(CourseScheduleDto courseScheduleDto){
        return mapper.map(courseScheduleDto, CourseSchedule.class);
    }
}

