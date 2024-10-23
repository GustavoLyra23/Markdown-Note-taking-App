package com.gustavolyra.markdown_api.dto;

import com.gustavolyra.markdown_api.entities.Note;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoteDto {

    private Long id;
    private String title;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
    }
}
