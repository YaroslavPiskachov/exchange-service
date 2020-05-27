package com.exchangeservice.controller;

import com.exchangeservice.dto.Error;
import com.exchangeservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GeneralControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleValidation(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ex -> new Error(ex.getDefaultMessage()))
                .collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PairRateIsNullException.class, PairCommissionIsNullException.class,
            InvalidExchangeAmountException.class, ExchangePairNotFoundException.class})
    public ResponseEntity<Error> handleExchangeServiceException(Exception exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGeneral(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(new Error("Exception occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
