package com.project.school.service;

import com.project.school.model.Student;

import java.util.List;

public interface IStudentService extends ICRUD <Student,Integer> {

    List<Student> findAllByOrderAgeDesc();


}
