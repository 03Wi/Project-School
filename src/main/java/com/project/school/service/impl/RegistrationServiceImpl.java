package com.project.school.service.impl;

import com.project.school.model.Registration;
import com.project.school.repository.IGenericRepo;
import com.project.school.repository.IRegistrationRepo;
import com.project.school.service.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
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

        var relCourse = courseAndStudentsRel
                    .stream()
                    .flatMap(r -> r.getDetailRegistration()
                            .stream()
                            .map(c -> Map.entry(c.getCourse().getName(), //Key
                                                r.getStudent().getName().concat(" ")
                                                        + r.getStudent().getLastName()))) //Value
                    .collect(groupingBy(
                            Map.Entry::getKey,
                            mapping(Map.Entry::getValue, toList())));


        //Order
        return relCourse.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new
                        ));
        }

}
