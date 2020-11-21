package com.example.fixprojectdemo1.web;

import com.example.fixprojectdemo1.Replace;

public class Calculator {

    @Replace(clazz = "com.example.fixprojectdemo1.Calculator",method = "calculator")
    public int calculator(){
        return 10;
    }
}
