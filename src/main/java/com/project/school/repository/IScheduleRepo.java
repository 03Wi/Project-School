package com.project.school.repository;

import com.project.school.model.Schedule;
import org.springframework.stereotype.Repository;

@Repository
public interface IScheduleRepo extends IGenericRepo<Schedule, Integer> {

}
