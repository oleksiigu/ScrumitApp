package com.scrumit.retro.repository;

import com.scrumit.retro.bean.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByTeamId(long id);
    Optional<Note> findByIdAndMeetingId(long id, long meetingId);
    List<Note> findAllByMeetingId(long id);
}
