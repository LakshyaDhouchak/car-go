package com.lakshya.car_go.ExceptionHandling;

public class ResourceNotFoundException extends RuntimeException{
    // define the constructor
    public ResourceNotFoundException(String s){
        super(s);
    }
}
