package com.scrumit.retro.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;
    private MeetingStatus status;

    @OneToMany(mappedBy = "meeting")
    private Set<Note> notes = new HashSet<>();

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date time;

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Meeting))
            return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(this.id, meeting.id) && Objects.equals(this.status, meeting.status)
                && Objects.equals(this.time, meeting.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.status, this.time);
    }
}
