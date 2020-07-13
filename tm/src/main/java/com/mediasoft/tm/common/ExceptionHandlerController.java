package com.mediasoft.tm.common;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationException(
            MethodArgumentNotValidException manve) {
        return new ResponseEntity<>("Ошибка валидации.. " +
                manve.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(
            HttpMessageNotReadableException hmnre)
            throws HttpMessageNotReadableException {
        return new ResponseEntity<>("Ошибка разбора запроса.. "
                + hmnre.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> otherException(Exception e)
            throws Exception {
        return new ResponseEntity<>("Произошло что-то непредвиденное.. "
                + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
