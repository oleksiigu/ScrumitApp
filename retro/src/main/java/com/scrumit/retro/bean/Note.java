package com.scrumit.retro.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Note {
    @Id
    @GeneratedValue
    private Long id;
    private Long teamId;
    private String text;
    private String action;
    private NoteType type;

    @ManyToOne
    @JoinColumn(name="meeting_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meeting meeting;

    @Column(name="DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateCreated;
}
