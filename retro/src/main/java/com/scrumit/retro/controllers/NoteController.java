package com.scrumit.retro.controllers;

import com.scrumit.retro.bean.Note;
import com.scrumit.retro.dto.UpdateNoteDto;
import com.scrumit.retro.exceptions.NoteNotFoundException;
import com.scrumit.retro.repository.MeetingRepository;
import com.scrumit.retro.repository.NoteRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class NoteController {

    private final NoteRepository repository;
    private final MeetingRepository meetingRepository;
    private final NoteModelAssembler assembler;

    NoteController(NoteRepository repository, MeetingRepository meetingRepository, NoteModelAssembler assembler) {
        this.repository = repository;
        this.meetingRepository = meetingRepository;
        this.assembler = assembler;
    }

    @GetMapping("/meetings/{meetingId}/notes")
    CollectionModel<EntityModel<Note>> all(@PathVariable(value = "meetingId") Long meetingId) {

        List<EntityModel<Note>> notes = repository.findAllByMeetingId(meetingId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notes,
                linkTo(methodOn(NoteController.class).all(meetingId)).withSelfRel());
    }

    @GetMapping("/meetings/{meetingId}/notes/{id}")
    EntityModel<Note> one(@PathVariable Long meetingId, @PathVariable Long id) {
        Note note = repository.findByIdAndMeetingId(id, meetingId).orElseThrow(() -> new NoteNotFoundException(id));
        return assembler.toModel(note);
    }

    @PostMapping("/meetings/{meetingId}/notes")
    EntityModel<Note> newNote(@PathVariable(value = "meetingId") Long meetingId, @RequestBody Note newNote) {
        return meetingRepository.findById(meetingId).map(meeting -> {
           newNote.setMeeting(meeting);
           return assembler.toModel(repository.save(newNote));
        }).orElseThrow(() -> new NoteNotFoundException(newNote.getId()));
    }

    @PutMapping("/meetings/{meetingId}/notes/{id}")
    EntityModel<Note> updateNote(@RequestBody UpdateNoteDto newNote, @PathVariable Long meetingId, @PathVariable Long id) {
        Note note = repository.findById(id)
                .map(existing -> {
                    existing.setText(newNote.getText());
                    existing.setAction(newNote.getAction());
                    existing.setType(newNote.getType());
                    return repository.save(existing);
                })
                .orElseGet(() -> {
                    Note createdNote = new Note();
                    createdNote.setText(newNote.getText());
                    createdNote.setAction(newNote.getAction());
                    createdNote.setType(newNote.getType());

                   return repository.save(createdNote);
                });

        return assembler.toModel(note);
    }


    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id){
        repository.deleteById(id);
    }
}
