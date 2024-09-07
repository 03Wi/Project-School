package com.project.school.controller;

import com.project.school.config.SwaggerConfig;
import com.project.school.dto.UserDto;
import com.project.school.model.User;
import com.project.school.security.WebSecurityConfig;
import com.project.school.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/school/user")
@RequiredArgsConstructor
@Tag(name = "USER", description = SwaggerConfig.description)
public class UserController {

    private final IUserService service;

    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> readAll(Pageable pageable){

        Page<User> page = service.findAll(pageable);
        Page<UserDto> userDtos = page.map(this::convertToDto);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> readById(@PathVariable("id") Integer id) {

        User user = service.findById(id);
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    @PostMapping("/save")
//    @PreAuthorize("@authServiceImpl.hasAccess('ADMIN')")
    public ResponseEntity<UserDto> save( @Valid @RequestBody UserDto userDto) {
        User param = service.save(convertToUser(userDto));
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
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto user, @PathVariable("id") Integer id) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        User param = service.update(convertToUser(user), id);
        return new ResponseEntity<>(convertToDto(param), HttpStatus.OK);
    }

    private UserDto convertToDto(User user){
        return mapper.map(user, UserDto.class);
    }
    private User convertToUser(UserDto userDto){
        return mapper.map(userDto, User.class);
    }
}

