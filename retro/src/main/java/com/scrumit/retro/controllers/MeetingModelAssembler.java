package com.scrumit.retro.controllers;


import com.scrumit.retro.bean.Meeting;
import com.scrumit.retro.bean.Note;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MeetingModelAssembler implements RepresentationModelAssembler<Meeting, EntityModel<Meeting>> {

    @Override
    public EntityModel<Meeting> toModel(Meeting meeting) {
        return EntityModel.of(meeting,
                linkTo(methodOn(MeetingController.class).one(meeting.getId())).withSelfRel(),
                linkTo(methodOn(MeetingController.class).all()).withRel("meetings"));
    }

}
