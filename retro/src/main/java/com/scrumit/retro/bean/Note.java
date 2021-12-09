package com.scrumit.retro.bean;

import lombok.*;

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

    @Column(name="DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dateCreated;
}
