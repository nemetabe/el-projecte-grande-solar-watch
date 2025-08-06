package com.nemetabe.solarwatch.controller;
import com.nemetabe.solarwatch.model.exception.InvalidCityException;
import com.nemetabe.solarwatch.model.exception.InvalidDateException;
import com.nemetabe.solarwatch.model.exception.MemberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvice {

    Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String memberNotFoundExceptionHandler(MemberNotFoundException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String cityNotFoundExceptionHandler(InvalidCityException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String invalidDateExceptionHandler(InvalidDateException ex) {return ex.getMessage();}
}
