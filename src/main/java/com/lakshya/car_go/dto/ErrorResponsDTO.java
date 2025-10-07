package com.lakshya.car_go.dto;

import java.time.Instant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponsDTO {
    // define the properties
    private String message;
    private int status;
    private String error;
    private Instant timeStamp;

    // define the constructor
    public ErrorResponsDTO(String message ,int status , String error , Instant timeStamp ){
        this.message = message;
        this.status = status;
        this.error = error;
        this.timeStamp = timeStamp;
    }

    // define the getMessage() methord
    public String getMessage(){
        return message;
    }

    // define the getStatus() methord
    public int getStatus(){
        return status;
    }

    // define the getError() methord
    public String getError(){
        return error;
    }

    // define the getTimeStamp() methord
    public Instant getTimeStamp(){
        return timeStamp;
    }
}
