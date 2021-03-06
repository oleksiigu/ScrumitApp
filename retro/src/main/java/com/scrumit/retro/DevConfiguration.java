package com.scrumit.retro;

import com.scrumit.retro.bean.Meeting;
import com.scrumit.retro.bean.MeetingStatus;
import com.scrumit.retro.bean.Note;
import com.scrumit.retro.bean.NoteType;
import com.scrumit.retro.repository.MeetingRepository;
import com.scrumit.retro.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(NoteRepository repository, MeetingRepository meetingRepository) {
        Note note = new Note();
        note.setType(NoteType.POSITIVE);
        note.setText("Sprint is good");
        note.setAction("-");

        Meeting meeting = new Meeting();
        meeting.setStatus(MeetingStatus.FINISHED);
        meeting.setNotes(new HashSet<>(Arrays.asList(note)));
        meetingRepository.save(meeting);

        note.setMeeting(meeting);

        return args -> {
            log.info(MessageFormat.format("Preloading {0}", repository.save(note)));
        };
    }
}
