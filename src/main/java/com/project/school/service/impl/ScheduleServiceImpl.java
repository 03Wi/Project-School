package com.project.school.service.impl;

import com.project.school.model.Schedule;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IScheduleRepo;
import com.project.school.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl extends CRUDImpl<Schedule, Integer> implements IScheduleService {

    private final IScheduleRepo repo;

    @Override
    public IGenericRepo<Schedule, Integer> getRepo() {

        return repo;
    }
}
