package com.project.school.repository;

import com.project.school.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepo extends IGenericRepo <Role, Integer>{

    Role findByName(String name);
}
