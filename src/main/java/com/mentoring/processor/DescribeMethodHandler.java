package com.mentoring.processor;

import com.mentoring.service.DefaultService;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by beerman on 23.11.2017.
 */

public class DescribeMethodHandler {
    private PackageWalkerUtil packageUtil = new PackageWalkerUtil();
    private final String pathPattern = "\\bcom\\b[.]\\S*[.]?\\bmodel\\b[.](?=[^.]*$)";
    private String path = "com.*.model.*";
    private static DefaultService service;

    public DefaultService getServiceInstance() throws ClassNotFoundException, IllegalAccessException {
        initializeAnnotatedService();
        Field annotatedField = null;
        ArrayList<Class> fitPatternClasses = findClassesByPattern();
        try {
            annotatedField = DefaultService.class.getDeclaredField("allMethods");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        List<String> collectedMethodInfo = new ArrayList<>();
        for(Class cl : fitPatternClasses){
            Method[] classMethods = cl.getDeclaredMethods();
            for(Method method : classMethods){
                String methodPrintInfo = Modifier.toString(method.getModifiers()) + " " + method.getReturnType() + " " + method.getName();
                System.out.println(methodPrintInfo);
                collectedMethodInfo.add(methodPrintInfo);
            }
        }
        annotatedField.set(service, collectedMethodInfo.toArray(new String[0]));
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

    private void initializeAnnotatedService() {
        try {
            service = DefaultService.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
