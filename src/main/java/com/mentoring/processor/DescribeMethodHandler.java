package com.mentoring.processor;

import com.mentoring.annotation.DescribeMethods;
import com.mentoring.service.DefaultService;
import com.mentoring.exception.CustomInstantiationException;

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
        ArrayList<Class> fitPatternClasses = findClassesByPattern(pathPattern);
        Field annotatedField = getAnnotatedField(DefaultService.class);
        List<String> collectedMethodInfo = getMethodInfo(fitPatternClasses);
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

    public Field getAnnotatedField(Class clazz) {
        Field annotatedField = null;
        Field[] declaredFields = clazz.getFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].isAnnotationPresent(DescribeMethods.class))
                annotatedField = declaredFields[i];
        }
        return annotatedField;
    }

    public List<String> getMethodInfo(ArrayList<Class> fitPatternClasses) {
        List<String> collectedMethodInfo = new ArrayList<>();
        for (Class cl : fitPatternClasses) {
            Method[] classMethods = cl.getDeclaredMethods();
            for (Method method : classMethods) {
                String methodPrintInfo = Modifier.toString(method.getModifiers()) + " " + method.getReturnType() + " " + method.getName();
                System.out.println(methodPrintInfo);
                collectedMethodInfo.add(methodPrintInfo);
            }
        }
        return collectedMethodInfo;
    }

    public ArrayList<Class> findClassesByPattern(String pathPattern) {
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
