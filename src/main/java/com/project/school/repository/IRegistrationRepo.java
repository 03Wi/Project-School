package com.project.school.repository;

import com.project.school.model.Course;
import com.project.school.model.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IRegistrationRepo extends IGenericRepo<Registration, Integer> {

}
