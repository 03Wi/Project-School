package com.project.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.school.dto.StudentDto;
import com.project.school.exception.ModelNotFoundException;
import com.project.school.model.Student;
import com.project.school.service.IStudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @MockBean(name = "defaultMapper")
    private ModelMapper mapper;

    @MockBean
    private IStudentService service;

    @Autowired
    private MockMvc mock;

    private List<Student> list;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentDto studentDto2;
    @BeforeEach
    void setup(){

       list = List.of(
                Student.builder()
                        .idStudent(1)
                        .name("name")
                        .dni("1000000000")
                        .lastName("lastName")
                        .age(22).build(),
                Student.builder()
                        .idStudent(2)
                        .name("name")
                        .dni("1000000001")
                        .lastName("lastName")
                        .age(21).build(),
                Student.builder()
                        .idStudent(3)
                        .name("name")
                        .dni("1000000002")
                        .lastName("lastName")
                        .age(24).build()
        );

         studentDto2 = StudentDto.builder()
                .idStudent(2)
                .name("name")
                .dni("1000000001")
                .lastName("lastName")
                .age(21).build();

        when(mapper.map(list.get(1),StudentDto.class)).thenReturn(studentDto2);

    }

    @DisplayName("Post student")
    @Test
    void saveStudent() throws Exception {

        when(service.save(any()))
                .thenReturn(list.get(1));
        when(mapper.map(list.get(1), StudentDto.class))
                .thenReturn(studentDto2);

        mock.perform(
                        post("/school/student/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writer().writeValueAsString(studentDto2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("name")));

    }
    @DisplayName("Read All")
    @Test
    void readAll() throws Exception {

        Pageable ps = PageRequest.of(0,1);
        when(service.findAll(ps)).thenReturn((Page<Student>) list);

        mock.perform(
                 get("/school/student/all")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("name")));

    }

    @DisplayName("Find by id")
    @Test
    void findById() throws Exception {

        when(service.findById(list.get(1).getIdStudent()))
                .thenReturn(list.get(1));
//        final int id = list.get(1).getIdStudent();
        mock.perform(
                get("/school/student/"+ list.get(1).getIdStudent())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")));
    }

    @DisplayName("ID not found")
    @Test
    void idNotFound() throws Exception {

        final int id = 92;

        when(service.findById(any()))
                .thenThrow( new ModelNotFoundException("ID not fount"+id));

        mock.perform(
                get("/school/student/"+ id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ModelNotFoundException));

    }


    @DisplayName("Delete by id")
    @Test
    void deleteById() throws Exception {

        final int id = 2;

        mock.perform(
                delete("/school/student/delete/"+id)
                        .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNoContent());

    }

    @DisplayName("Update student")
    @Test
    void updateStudent() throws Exception {

        final int id = 2;

        when(service.update(any(), any()))
                .thenReturn(list.get(1));
        when(mapper.map(studentDto2, StudentDto.class))
                .thenReturn(studentDto2);

        mock.perform(
                put("/school/student/update/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(studentDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is(21)));

    }

}
