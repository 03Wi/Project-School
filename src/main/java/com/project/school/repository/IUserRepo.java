package com.project.school.repository;

import com.project.school.model.User;

public interface IUserRepo extends IGenericRepo<User, Integer> {

    User findByUserName(String username);
}
