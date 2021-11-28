package com.scrumit.retro.dto;

import com.scrumit.retro.bean.NoteType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteDto {
    private String text;
    private String action;
    private NoteType type;
}
