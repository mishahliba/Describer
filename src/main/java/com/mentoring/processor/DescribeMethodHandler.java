package com.mentoring.processor;

import com.mentoring.annotation.DescribeMethods;
import com.mentoring.service.DefaultService;
import exception.CustomInstantiationException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by beerman on 23.11.2017.
 */

public class DescribeMethodHandler {
    private PackageWalkerUtil packageUtil = new PackageWalkerUtil();
    private final String pathPattern = "\\bcom\\b[.]\\S*[.]?\\bmodel\\b[.](?=[^.]*$)";
    private String path = "com.*.model.*";

    public DefaultService getServiceInstance() {
        DefaultService service = (DefaultService)initializeAnnotatedService(DefaultService.class);
        Field annotatedField = null;
        ArrayList<Class> fitPatternClasses = findClassesByPattern();
        Field[] declaredFields = DefaultService.class.getFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].isAnnotationPresent(DescribeMethods.class))
                annotatedField = declaredFields[i];
        }
        List<String> collectedMethodInfo = new ArrayList<>();
        for (Class cl : fitPatternClasses) {
            Method[] classMethods = cl.getDeclaredMethods();
            for (Method method : classMethods) {
                String methodPrintInfo = Modifier.toString(method.getModifiers()) + " " + method.getReturnType() + " " + method.getName();
                System.out.println(methodPrintInfo);
                collectedMethodInfo.add(methodPrintInfo);
            }
        }
        try {
            annotatedField.set(service, collectedMethodInfo.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            try {
                throw new CustomInstantiationException("cannot set value");
            } catch (CustomInstantiationException e1) {
                e.getMessage();
            }
        }
        return service;
    }

    private ArrayList<Class> findClassesByPattern() {
        ArrayList<Class> matchedByPattern = new ArrayList<>();
        Pattern pattern = Pattern.compile(pathPattern);
        String searchPrefix = path.split("[.]")[0];
        Matcher matcher;
        Class[] projectClasses = packageUtil.getClasses(searchPrefix);
        for (Class c : projectClasses) {
            matcher = pattern.matcher(c.getName());
            if (matcher.lookingAt())
                matchedByPattern.add(c);
        }
        return matchedByPattern;
    }

    public Object initializeAnnotatedService(Class clazz) {
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException i) {
            throw new RuntimeException("cannot initialize class");

        }
        return instance;
    }
}
