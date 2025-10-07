package com.lakshya.car_go.ExceptionHandling;

public class CarUnavailableException extends RuntimeException{
    // define the constructor
    public CarUnavailableException(String s){
        super(s);
    }
}
