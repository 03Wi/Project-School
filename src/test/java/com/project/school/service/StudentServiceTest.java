package com.project.school.service;

import com.project.school.model.Student;
import com.project.school.repository.IStudentRepo;
import com.project.school.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private IStudentRepo repo;

    @InjectMocks
    private StudentServiceImpl service;

    private List<Student> list;

    private Student student;

    @BeforeEach
    void setup(){

        student = Student.builder()
                .idStudent(4)
                .name("name")
                .dni("1000000004")
                .lastName("lastName")
                .age(22).build();

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

    }

    @Test
    void orderAgeStudents() {

        when(repo.findAll()).thenReturn(list);
        List<Student> result = service.findAllByOrderAgeDesc();

        assertEquals(3, result.size());
        assertEquals("name", result.get(0).getName());
        assertEquals("name", result.get(1).getName());
        assertEquals("name", result.get(2).getName());
    }


    @DisplayName("Update")
    @Test
    void update () throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        when(repo.findById(any())).thenReturn(Optional.of(student));
        repo.findById(4).get().setAge(31);
        when(repo.save(any())).thenReturn(student);

        Student result = service.update(student, student.getIdStudent());

        assertEquals(31, result.getAge());
        assertEquals("name", result.getName());
        assertEquals("1000000004", result.getDni());
    }

    @DisplayName("save")
    @Test
    void save () throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        when(repo.save(any())).thenReturn(student);
        Student result = service.save(student);

        assertNotNull(result);
    }

}
