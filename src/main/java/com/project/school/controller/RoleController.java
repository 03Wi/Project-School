package com.project.school.controller;

import com.project.school.dto.RoleDto;
import com.project.school.model.Role;
import com.project.school.service.IRoleService;
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
@RequestMapping("/school/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<RoleDto>> readAll(Pageable pageable){

        Page<Role> page = service.findAll(pageable);
        Page<RoleDto> roleDtos = page.map(this::convertToDto);
        return new ResponseEntity<>(roleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> readById(@PathVariable("id") Integer id) {

        Role role = service.findById(id);
        return new ResponseEntity<>(convertToDto(role), HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<RoleDto> save( @Valid @RequestBody RoleDto roleDto) {

        Role param = service.save(convertToRole(roleDto));
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
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto role, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Role param = service.update(convertToRole(role), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private RoleDto convertToDto(Role role){
        return mapper.map(role, RoleDto.class);
    }
    private Role convertToRole(RoleDto roleDto){
        return mapper.map(roleDto, Role.class);
    }
}

