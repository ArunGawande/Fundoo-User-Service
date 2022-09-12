package com.bridgelabz.fundoouserservice.exception.exceptionhandler;

import com.bridgelabz.fundoouserservice.exception.CustomException;
import com.bridgelabz.fundoouserservice.exception.FundooUserNotFoundException;
import com.bridgelabz.fundoouserservice.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserException {

    @ExceptionHandler(FundooUserNotFoundException.class)
    public ResponseEntity<ResponseUtil> handlerHiringException(FundooUserNotFoundException exception) {
        ResponseUtil response = new ResponseUtil();
        response.setErrorCode(400);
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> customValidationException(MethodArgumentNotValidException exception) {
        CustomException customException = new CustomException();
        customException.setErrorCode(400);
        customException.setMessage(exception.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }


}
