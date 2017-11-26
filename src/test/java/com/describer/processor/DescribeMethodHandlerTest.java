package com.describer.processor;

import com.describer.annotation.DescribeMethods;
import com.describer.model.Account;
import com.describer.service.DefaultService;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class covers functionality of @see com.describer.processor.DescribeMethodHandler
 * and @see com.describer.processor.PackageWalkerUtil
 */
public class DescribeMethodHandlerTest {
    private DescribeMethodHandler methodHandler = new DescribeMethodHandler();
    private String[] POJOMethods = new String[]{"public class java.security.Timestamp getLastLogin",
            "public void setLastLogin", "public class java.lang.String getPassword",
            "public void setPassword", "public class java.lang.String getUserName", "public void setUserName"};

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = RuntimeException.class)
    public void negativeTestServiceInstantiation() {
        methodHandler.initializeAnnotatedService(null);
    }

    @Test(expected = RuntimeException.class)
    public void negativeTestMessageServiceInstantiation() {
        methodHandler.initializeAnnotatedService(DescribeMethods.class);
        exception.expectMessage("cannot initialize class");
    }

    @Test
    public void positiveReturnValueServiceInstantiation() {
        DefaultService service = (DefaultService) methodHandler.initializeAnnotatedService(DefaultService.class);
        assertEquals(service.getClass(), DefaultService.class);
    }

    @Test(expected = NullPointerException.class)
    public void findClassesByPatternNullInput() {
        methodHandler.findByPattern(null);
    }

    @Test
    public void findClassByPatternCorrectPattern() {
        List<Class> foundClasses = methodHandler.findByPattern("com.*.model.*");
        assertArrayEquals(new Class[]{Account.class}, foundClasses.toArray());
    }

    @Test
    public void findClassByPatternIncorrectPattern() {
        List<Class> foundClasses = methodHandler.findByPattern("org.*.model.*");
        assertEquals(foundClasses, new ArrayList<Class>());
    }

    @Test
    public void getServiceInstanceTest() {
        DefaultService service = methodHandler.getServiceInstanceCopy();
        assertNotNull(service);
    }

    @Test
    public void getServiceInstanceFieldNotNull() {
        DefaultService service = methodHandler.getServiceInstanceCopy();
        assertNotNull(service.allMethods);
    }

    @Test
    public void getServiceInstanceFieldLengthCorrect() {
        DefaultService service = methodHandler.getServiceInstanceCopy();
        assertEquals(service.allMethods.length, POJOMethods.length);
    }

    @Test
    public void getServiceInstanceFieldValueCorrect() {
        DefaultService service = methodHandler.getServiceInstanceCopy();
        List<String> allMethodsFound = Arrays.asList(service.allMethods);
        List<String> allMethodsActual = Arrays.asList(POJOMethods);
        for (String method : allMethodsActual) {
            System.out.println(method);
            assertTrue(allMethodsFound.contains(method));
        }
    }
}

