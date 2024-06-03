package com.project.school.service.impl;

import com.project.school.model.Teacher;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.ITeacherRepo;
import com.project.school.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends CRUDImpl<Teacher, Integer> implements ITeacherService {

    private final ITeacherRepo repo;

    @Override
    public IGenericRepo<Teacher, Integer> getRepo() {

        return repo;
    }

}
