package com.scrumit.retro.advice;

import com.scrumit.retro.exceptions.MeetingNotFoundException;
import com.scrumit.retro.exceptions.NoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MeetingNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MeetingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String meetingNotFoundHandler(MeetingNotFoundException exception){
        return exception.getMessage();
    }
}
