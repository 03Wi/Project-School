package com.project.school.service.impl;

import com.project.school.exception.ModelNotFoundException;
import com.project.school.model.Student;
import com.project.school.repository.IGenericRepo;
import com.project.school.service.ICRUD;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class CRUDImpl <T, ID> implements ICRUD <T, ID>  {

    public abstract IGenericRepo<T, ID> getRepo();
    @Override
    public List<T> findAll() {

        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {

        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Id not found "+ id));
    }

    @Override
    public void deleteById(ID id) {

        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Id not found "+id));
        getRepo().deleteById(id);
    }

    @Override
    public T save(T t) {

        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> clazz = t.getClass();
        String nameMethod = "setId" + clazz.getSimpleName();
        Method method = clazz.getMethod(nameMethod, id.getClass());
        method.invoke(t, id);

        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("Id not found "+id));
        return getRepo().save(t);
    }
}
