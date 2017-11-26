package com.describer.processor;

import com.describer.annotation.DescribeMethods;
import com.describer.service.DefaultService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for creation of DefaultService copy and
 * providing allMethod field with info about methods of specific classes
 */

public class DescribeMethodHandler {

    private PackageWalkerUtil packageUtil;
    private static final String pathPattern = "\\bcom\\b[.]\\S*[.]?\\bmodel\\b[.](?=[^.]*$)";
    private static final String path = "com.*.model.*";

    public DescribeMethodHandler() {
        packageUtil = new PackageWalkerUtil();
    }

    public DefaultService getServiceInstanceCopy() {
        DefaultService service = (DefaultService) initializeAnnotatedService(DefaultService.class);
        List<Class> fitPatternClasses = findByPattern(pathPattern);
        Field annotatedField = getAnnotatedField(DefaultService.class);
        List<String> collectedMethodInfo = getMethodInfo(fitPatternClasses);
        try {
            annotatedField.set(service, collectedMethodInfo.toArray(new String[0]));
        } catch (Exception e) {
            e.getCause();
        }
        return service;
    }

    public Field getAnnotatedField(Class clazz) {
        Field annotatedField = null;
        Field[] declaredFields = clazz.getFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].isAnnotationPresent(DescribeMethods.class)) {
                annotatedField = declaredFields[i];
                break;
            }
        }
        return annotatedField;
    }

    public List<String> getMethodInfo(List<Class> fitPatternClasses) {
        List<String> collectedMethodInfo = new ArrayList<>();
        for (Class cl : fitPatternClasses) {
            Method[] classMethods = cl.getDeclaredMethods();
            for (Method method : classMethods) {
                String methodPrintInfo = Modifier.toString(method.getModifiers()) + " " + method.getReturnType() + " " + method.getName();
                collectedMethodInfo.add(methodPrintInfo);
            }
        }
        return collectedMethodInfo;
    }

    public List<Class> findByPattern(String pathPattern) {
        List<Class> matchedByPattern = new ArrayList<>();
        Pattern pattern = Pattern.compile(pathPattern);
        String searchPrefix = path.split("[.]")[0];
        Matcher matcher;
        Class[] projectClasses = packageUtil.getClasses(searchPrefix);
        for (Class clazz : projectClasses) {
            matcher = pattern.matcher(clazz.getName());
            if (matcher.lookingAt())
                matchedByPattern.add(clazz);
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
