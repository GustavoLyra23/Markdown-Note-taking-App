package com.gustavolyra.markdown_api.repositories;

import com.gustavolyra.markdown_api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
