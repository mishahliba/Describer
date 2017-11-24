package com.mentoring.processor;

import exception.CustomInstantiationException;
import org.junit.*;
import org.junit.rules.ExpectedException;


/**
 * Created by myhl0716 on 11/24/2017.
 */
public class DescribeMethodHandlerTest {
    DescribeMethodHandler methodHandler = new DescribeMethodHandler();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = RuntimeException.class)
    public void negativeTestServiceInstantiation() throws CustomInstantiationException {
        methodHandler.initializeAnnotatedService(null);
    }

    @Test
    public void negativeTestMessageServiceInstantiation(){
        methodHandler.initializeAnnotatedService(null);
        //exception.expect();
    }


}

