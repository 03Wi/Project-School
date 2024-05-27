package com.project.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface ICRUD <T, ID>  {

    Page<T> findAll(Pageable pageable);
    T findById (ID id);
    void deleteById(ID id);
    T save(T t);
    T update(T t, ID id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
