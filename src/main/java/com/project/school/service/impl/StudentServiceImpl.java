package com.project.school.service.impl;

import com.project.school.model.Student;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IStudentRepo;
import com.project.school.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, Integer> implements IStudentService {

    private final IStudentRepo repo;
    @Override
    public IGenericRepo<Student, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Student> findAllByOrderAgeDesc() {
        return getRepo()
                .findAll()
                .stream()
                .sorted(Comparator
                        .comparing(Student::getAge)
                        .reversed())
                .toList();
    }
}
