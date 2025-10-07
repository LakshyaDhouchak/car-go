package com.lakshya.car_go.ExceptionHandling;

public class InvalidInputException  extends RuntimeException{
    // define the constructor
    public InvalidInputException(String s){
        super(s);
    }
}
