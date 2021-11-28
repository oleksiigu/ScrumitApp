package com.scrumit.retro.advice;

import org.springframework.http.HttpStatus;
import com.scrumit.retro.exceptions.NoteNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoteNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noteNotFoundHandler(NoteNotFoundException exception){
        return exception.getMessage();
    }
}
