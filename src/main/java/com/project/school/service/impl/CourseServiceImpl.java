package com.project.school.service.impl;

import com.project.school.model.Course;
import com.project.school.repository.ICourseRepo;
import com.project.school.repository.IGenericRepo;
import com.project.school.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, Integer> implements ICourseService {

    private final ICourseRepo repo;
    @Override
    public IGenericRepo<Course, Integer> getRepo() {
        return repo;
    }
}
