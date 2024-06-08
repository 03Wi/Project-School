package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.ScheduleDto;
import com.project.school.model.Schedule;
import com.project.school.service.IScheduleService;
import com.project.school.service.IScheduleService;
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
@RequestMapping("/school/schedule")
@RequiredArgsConstructor
@PreAuthorize("@authServiceImpl.hasAccess('USER, ADMIN')")
@Tag(name = "Schedule", description = SwaggerConfig.description)
public class ScheduleController {

    private final IScheduleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<ScheduleDto>> readAll(Pageable pageable){

        Page<Schedule> page = service.findAll(pageable);
        Page<ScheduleDto> scheduleDtos = page.map(this::convertToDto);
        return new ResponseEntity<>(scheduleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> readById(@PathVariable("id") Integer id) {

        Schedule schedule = service.findById(id);
        return new ResponseEntity<>(convertToDto(schedule), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<ScheduleDto> save( @Valid @RequestBody ScheduleDto scheduleDto) {

        Schedule param = service.save(convertToSchedule(scheduleDto));
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
    public ResponseEntity<ScheduleDto> update(@Valid @RequestBody ScheduleDto schedule, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Schedule param = service.update(convertToSchedule(schedule), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private ScheduleDto convertToDto(Schedule schedule){
        return mapper.map(schedule, ScheduleDto.class);
    }
    private Schedule convertToSchedule(ScheduleDto scheduleDto){
        return mapper.map(scheduleDto, Schedule.class);
    }
}

