package com.project.school.repository;

import com.project.school.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DataJpaTest
public class StudentRepositoryTest {


    @MockBean
    private IStudentRepo repo;

    private Student student;

    private Student studentTwo;

    @BeforeEach
    void setup(){

        student = Student.builder()
                .idStudent(1)
                .name("name")
                .dni("1000000000")
                .lastName("lastName")
                .age(2).build();

        studentTwo = Student.builder()
                .idStudent(2)
                .name("name")
                .dni("1000000001")
                .lastName("lastName")
                .age(2).build();

    }

    @DisplayName("Test save a student")
    @Test
    void saveStudent(){

        when(repo.save(student)).thenReturn(student);
        Student result = repo.save(student);

        assertThat(result)
                .isNotNull();
        assertThat(result.getName().toLowerCase())
                .isEqualTo("name");
    }

    @DisplayName("Test read all students")
    @Test
    void readAll(){

        List<Student> result = List.of(student, studentTwo);
        BDDMockito.given(repo.findAll()).willReturn(result);
//      when(repo.findAll()).thenReturn(result);

        List<Student> resultList = repo.findAll();

        assertThat(resultList).isNotNull();
        assertThat(resultList.get(1).getName()).isEqualTo("name");
        assertThat(resultList.size())
                .isEqualTo(2);
    }

    @DisplayName("Test student find by id")
    @Test
    void findById(){

//        when(repo.save(student)).thenReturn(student);
        when(repo.findById(student.getIdStudent())).thenReturn(Optional.of(student));
        Student result = repo.findById(student.getIdStudent()).get();

        assertThat(result).isNotNull();
        assertThat(result.getIdStudent()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("name");
    }

    @DisplayName("Delete student by id")
    @Test
    void deleteById(){

        repo.deleteById(1);  //any()
        verify(repo, times(1))
                    .deleteById(student.getIdStudent());

    }








}
