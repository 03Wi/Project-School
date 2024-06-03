package com.project.school.service;

import com.project.school.model.Student;
import com.project.school.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStudentService extends ICRUD <Student,Integer> {

    List<Student> findAllByOrderAgeDesc(Pageable pageable);


}
