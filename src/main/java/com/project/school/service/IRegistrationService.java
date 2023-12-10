package com.project.school.service;

import com.project.school.model.Registration;

import java.util.List;
import java.util.Map;

public interface IRegistrationService extends ICRUD <Registration,Integer> {

    Map<String, List<String>> courseAndStudent();

}
