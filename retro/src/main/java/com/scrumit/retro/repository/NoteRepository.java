package com.scrumit.retro.repository;

import com.scrumit.retro.bean.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByTeamId(long id);
}
