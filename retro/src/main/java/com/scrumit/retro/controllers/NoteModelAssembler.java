package com.scrumit.retro.controllers;

import com.scrumit.retro.bean.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {

    @Override
    public EntityModel<Note> toModel(Note note) {
        return EntityModel.of(note,
                linkTo(methodOn(NoteController.class).one(note.getMeeting().getId(),note.getId())).withSelfRel(),
                linkTo(methodOn(NoteController.class).all(note.getMeeting().getId())).withRel("notes"));
    }
}
