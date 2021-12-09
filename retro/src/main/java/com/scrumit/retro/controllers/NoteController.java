package com.scrumit.retro.controllers;

import com.scrumit.retro.bean.Note;
import com.scrumit.retro.dto.UpdateNoteDto;
import com.scrumit.retro.exceptions.NoteNotFoundException;
import com.scrumit.retro.repository.NoteRepository;
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

    private final NoteModelAssembler assembler;

    NoteController(NoteRepository repository, NoteModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/notes")
    CollectionModel<EntityModel<Note>> all(){
        List<EntityModel<Note>> notes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notes,
                linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @GetMapping("/notes/{id}")
    EntityModel<Note> one(@PathVariable Long id) {
        Note note = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return assembler.toModel(note);
    }

    @PostMapping("/notes")
    EntityModel<Note> newNote(@RequestBody Note newNote) {
        return assembler.toModel(repository.save(newNote));
    }

    @PutMapping("/notes/{id}")
    EntityModel<Note> updateNote(@RequestBody UpdateNoteDto newNote, @PathVariable Long id){
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
