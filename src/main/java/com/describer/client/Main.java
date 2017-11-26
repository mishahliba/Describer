package com.describer.client;

import com.describer.processor.DescribeMethodHandler;
import com.describer.service.DefaultService;
import java.util.Arrays;

/**
 * example of initializing class through reflection. DefaultService doesn`t provide any logic by itself.
 * All work happens in DescribeMethodHandler. As a result, DefaultService instance appears filled with info about
 * methods of specific classes
 */

public class Main {
    public static void main(String[] args){
        DescribeMethodHandler handler = new DescribeMethodHandler();
        DefaultService service = handler.getServiceInstanceCopy();
        System.out.println(Arrays.toString(service.allMethods));
    }
}
