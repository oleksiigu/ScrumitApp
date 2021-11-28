package com.scrumit.retro.controllers;

import com.scrumit.retro.bean.Note;
import com.scrumit.retro.dto.UpdateNoteDto;
import com.scrumit.retro.exceptions.NoteNotFoundException;
import com.scrumit.retro.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    private final NoteRepository repository;

    NoteController(NoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/notes")
    Iterable<Note> all(){
        return repository.findAll();
    }

    @GetMapping("/notes/{id}")
    Note one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
    }

    @PostMapping("/notes")
    Note newNote(@RequestBody Note newNote) {
        return repository.save(newNote);
    }

    @PutMapping("/notes/{id}")
    Note updateNote(@RequestBody UpdateNoteDto newNote, @PathVariable Long id){
        return repository.findById(id)
                .map(note -> {
                    note.setText(newNote.getText());
                    note.setAction(newNote.getAction());
                    note.setType(newNote.getType());
                    return repository.save(note);
                })
                .orElseGet(() -> {
                    Note createdNote = Note.builder()
                            .text(newNote.getText())
                            .action(newNote.getAction())
                            .type(newNote.getType())
                            .build();
                   return repository.save(createdNote);
                });
    }


    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id){
        repository.deleteById(id);
    }
}
