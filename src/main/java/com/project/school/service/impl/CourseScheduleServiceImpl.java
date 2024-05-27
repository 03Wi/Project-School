package com.project.school.service.impl;

import com.project.school.model.CourseSchedule;
import com.project.school.repository.ICourseScheduleRepo;
import com.project.school.repository.IGenericRepo;
import com.project.school.service.ICourseScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseScheduleServiceImpl extends CRUDImpl<CourseSchedule, Integer> implements ICourseScheduleService {

    private final ICourseScheduleRepo repo;

    @Override
    public IGenericRepo<CourseSchedule, Integer> getRepo() {

        return repo;
    }
}
