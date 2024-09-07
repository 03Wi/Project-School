package com.project.school.service.impl;

import com.project.school.model.User;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IUserRepo;
import com.project.school.security.WebSecurityConfig;
import com.project.school.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo;

    @Override
    public IGenericRepo<User, Integer> getRepo() {

        return repo;
    }

    @Override
    public User save(User user) {
        User userModify = extracted(user);
        return repo.save(userModify);
    }

    private User extracted(User user) {
        String encode = WebSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        return user;
    }
}
