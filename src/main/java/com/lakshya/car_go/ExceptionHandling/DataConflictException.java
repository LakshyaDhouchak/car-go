package com.lakshya.car_go.ExceptionHandling;

public class DataConflictException extends RuntimeException {
    // define the constructor
    public DataConflictException(String s){
        super(s);
    }
}
