package com.nemetabe.solarwatch.controller;
import com.nemetabe.solarwatch.model.exception.city.AlreadyExistInDatabaseException;
import com.nemetabe.solarwatch.model.exception.city.CityNotFoundException;
import com.nemetabe.solarwatch.model.exception.city.InvalidCityException;
import com.nemetabe.solarwatch.model.exception.city.SavedCityNotFoundException;
import com.nemetabe.solarwatch.model.exception.date.InvalidDateException;
import com.nemetabe.solarwatch.model.exception.member.MemberNotFoundException;
import com.nemetabe.solarwatch.model.exception.solar.SolarTimesNotFoundException;
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

//    @ResponseBody
//    @ExceptionHandler(WebClientResponseException) TODO
    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String invalidCityExceptionHandler(InvalidCityException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cityNotFoundExceptionHandler(CityNotFoundException ex) {return ex.getMessage();}


    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String invalidDateExceptionHandler(InvalidDateException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(SavedCityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String savedCityNotFoundExceptionHandler(SavedCityNotFoundException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(SolarTimesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String solarTimesNotFoundExceptionHandler(SolarTimesNotFoundException ex) {return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(AlreadyExistInDatabaseException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public String alreadyExistExceptionHandler(AlreadyExistInDatabaseException ex) {return ex.getMessage();}



}
