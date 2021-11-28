package com.scrumit.retro.repository;

import com.scrumit.retro.bean.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NoteRepository extends CrudRepository<Note, Long> {
    Optional<Note> findByTeamId(long id);
}
