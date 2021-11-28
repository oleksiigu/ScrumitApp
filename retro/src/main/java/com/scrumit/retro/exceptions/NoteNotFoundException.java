package com.scrumit.retro.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("Couldn't find note " + id);
    }
}
