package com.mentoring.client;

import com.mentoring.processor.DescribeMethodHandler;
import com.mentoring.service.DefaultService;

/**
 * Created by beerman on 23.11.2017.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException {
        DescribeMethodHandler handler = new DescribeMethodHandler();
        DefaultService service = null;
        service = handler.getServiceInstance();
        String [] s = service.allMethods;


    }
}
