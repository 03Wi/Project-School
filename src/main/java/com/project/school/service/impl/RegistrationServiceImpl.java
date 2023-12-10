package com.project.school.service.impl;

import com.project.school.model.Registration;
import com.project.school.model.Student;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IRegistrationRepo;
import com.project.school.service.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl extends CRUDImpl<Registration, Integer> implements IRegistrationService {

    private final IRegistrationRepo repo;
    @Override
    public IGenericRepo<Registration, Integer> getRepo() {
        return repo;
    }

    @Override
    public Map<String, List<String>> courseAndStudent() {

        List<Registration> courseAndStudentsRel = getRepo().findAll(); //Results BD

        return courseAndStudentsRel
                    .stream()
                    .flatMap(r -> r.getDetailRegistration() //Se podria corregir para dejar el value como el object :33
                            .stream()
                            .map(c -> Map.entry(c.getCourse().getName(), //Key
                                                r.getStudent().getName() +" "+ r.getStudent().getLastName()))) //Value
                    .collect(groupingBy(Map.Entry::getKey,
                             mapping(Map.Entry::getValue, toList())));

        }
}
