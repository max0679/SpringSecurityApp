package ru.maslenikov.springjwttoken.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maslenikov.springjwttoken.exceptions.BaseException;
import ru.maslenikov.springjwttoken.util.BaseExceptionResponse;

import java.net.http.HttpResponse;

public class BaseController {

    @ExceptionHandler
    public ResponseEntity<BaseExceptionResponse> baseExceptionHandler(BaseException exception) {
        BaseExceptionResponse response = new BaseExceptionResponse(exception.getMessage());
        return new ResponseEntity<BaseExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }


}
