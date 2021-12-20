package br.com.letscode.starwars.socialnetwork.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.letscode.starwars.socialnetwork.dto.ExceptionDto;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<ExceptionDto> handleIllegalState(RuntimeException ex) {
    	return new ResponseEntity<>(ExceptionDto.builder().message(ex.getMessage()).status(HttpStatus.PRECONDITION_FAILED.value()).build(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDto> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ExceptionDto.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDto> handleInternalServerError(Exception ex) {
        return new ResponseEntity<>(ExceptionDto.builder().message(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
