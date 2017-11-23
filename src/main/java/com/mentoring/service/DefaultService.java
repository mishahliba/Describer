package com.mentoring.service;

/**
 * Created by beerman on 23.11.2017.
 */

import com.mentoring.annotation.DescribeMethods;
import com.mentoring.model.MyAccount;

public class DefaultService {
    @DescribeMethods(MyAccount.class) // "*.*.task", "com.project.*.task.*"
    public String[] allMethods;


}
