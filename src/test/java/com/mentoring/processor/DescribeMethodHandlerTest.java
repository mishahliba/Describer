package com.mentoring.processor;

import com.mentoring.annotation.DescribeMethods;
import com.mentoring.exception.CustomInstantiationException;
import com.mentoring.model.MyAccount;
import com.mentoring.service.DefaultService;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by myhl0716 on 11/24/2017.
 */
public class DescribeMethodHandlerTest {
    DescribeMethodHandler methodHandler = new DescribeMethodHandler();
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
        methodHandler.findClassesByPattern(null);
    }

    @Test
    public void findClassByPatternCorrectPattern() {
        List<Class> foundClasses = methodHandler.findClassesByPattern("com.*.model.*");
        assertArrayEquals(new Class[]{MyAccount.class}, foundClasses.toArray());
    }

    @Test
    public void getServiceInstanceTest() {
        DefaultService service = methodHandler.getServiceInstance();
        assertNotNull(service);
    }

    @Test
    public void getServiceInstanceFieldNotNull() {
        DefaultService service = methodHandler.getServiceInstance();
        assertNotNull(service.allMethods);
    }

    @Test
    public void getServiceInstanceFieldValueCorrect() {
        DefaultService service = methodHandler.getServiceInstance();
        String[] POJOMethods = new String[]{"public void methodA",
                "public void methodB", "public void methodC",
                "public void methodD"};
        //Assert.assertTrue(service.allMethods.);
        assertEquals(POJOMethods[0], service.allMethods[0]);
    }
}

