package com.project.school.service.impl;

import com.project.school.model.Role;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IRoleRepo;
import com.project.school.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleImpl extends CRUDImpl<Role, Integer> implements IRoleService {

    private final IRoleRepo repo;

    @Override
    public IGenericRepo<Role, Integer> getRepo() {
        return repo;
    }

}
