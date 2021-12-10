package com.scrumit.retro.controllers;

import com.scrumit.retro.bean.Meeting;
import com.scrumit.retro.bean.Note;
import com.scrumit.retro.exceptions.MeetingNotFoundException;
import com.scrumit.retro.repository.MeetingRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MeetingController {

    private MeetingRepository meetingRepository;
    private MeetingModelAssembler meetingModelAssembler;

    public MeetingController(MeetingRepository meetingRepository, MeetingModelAssembler assembler) {
        this.meetingModelAssembler = assembler;
        this.meetingRepository = meetingRepository;
    }

    @GetMapping("/meetings/{id}")
    public EntityModel<Meeting> one(Long id) {
        Meeting meeting = meetingRepository.getById(id);
        return meetingModelAssembler.toModel(meeting);
    }

    @GetMapping("/meetings")
    CollectionModel<EntityModel<Meeting>> all() {
        List<EntityModel<Meeting>> meetings = meetingRepository.findAll().stream()
                .map(meetingModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(meetings,
                linkTo(methodOn(MeetingController.class).all()).withSelfRel());
    }

    @PostMapping("/meetings")
    EntityModel<Meeting> newMeeting(@RequestBody Meeting meeting){
        return meetingModelAssembler.toModel(meetingRepository.save(meeting));
    }

    @DeleteMapping("/meetings/{meetingId}")
    ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId) {
        return meetingRepository.findById(meetingId).map(meeting -> {
            meetingRepository.delete(meeting);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new MeetingNotFoundException(meetingId));
    }
}
