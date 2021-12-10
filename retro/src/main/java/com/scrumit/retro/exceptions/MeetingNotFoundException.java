package com.scrumit.retro.exceptions;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(Long id){
        super("Meeting not found " + id);
    }
}
