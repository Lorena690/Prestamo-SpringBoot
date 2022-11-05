package com.ceiba.biblioteca.infrastructure.exceptions;

import com.ceiba.biblioteca.aplication.dto.response.ErrorResponse;
import com.ceiba.biblioteca.domain.exception.PrestamoException;
import com.ceiba.biblioteca.domain.exception.PrestamoNotFoundEx;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.concurrent.ConcurrentHashMap;

import static com.ceiba.biblioteca.domain.constants.PrestamoConstants.*;

@ControllerAdvice
public class PrestamoExceptionHandler extends ResponseEntityExceptionHandler  {


    private static final ConcurrentHashMap<String, Integer> EXCEPTIONS = new ConcurrentHashMap<>();

    public PrestamoExceptionHandler() {
        EXCEPTIONS.put(PrestamoException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(HttpMessageNotReadableException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(MethodArgumentNotValidException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(PrestamoNotFoundEx.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return prestamoExceptionHandler(ex);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return prestamoExceptionHandler(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return prestamoExceptionHandler(ex);
    }

    private ResponseEntity<Object> prestamoExceptionHandler(Exception ex){
        ResponseEntity<Object> response;

        String exceptionName = ex.getClass().getSimpleName();
        String message = getErrorMessage(ex);
        Integer code = EXCEPTIONS.get(exceptionName);

        if (code != null) {
            ErrorResponse error = new ErrorResponse(message);
            response = new ResponseEntity<>(error, HttpStatus.valueOf(code));
        } else {
            ErrorResponse error = new ErrorResponse(INTERNAL_ERROR);
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private String getErrorMessage(Exception ex){
        if(ex.getMessage().contains(ERROR_TIPO_USUARIO)){
            return ERROR_TIPO_USUARIO;
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            FieldError fieldError = exception.getBindingResult().getFieldError();
            if(!ObjectUtils.isEmpty(fieldError)){
                return String.format(MESSAGE_TEMPLATE, fieldError.getField(), fieldError.getDefaultMessage());
            }else{
                return INTERNAL_ERROR;
            }
        } else if (ex instanceof PrestamoException) {
            return ex.getMessage();
        } else if (ex instanceof PrestamoNotFoundEx) {
            return ex.getMessage();
        } else {
            return INTERNAL_ERROR;
        }
    }




}
