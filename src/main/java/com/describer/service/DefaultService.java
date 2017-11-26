package com.describer.service;

import com.describer.annotation.DescribeMethods;

/**
 * service class that collects method names and signature from classes in specific package.
 * For collection purpose reflection is used.
 */

public class DefaultService {
    @DescribeMethods // "*.*.task", "com.project.*.task.*"
    public String[] allMethods;
}
