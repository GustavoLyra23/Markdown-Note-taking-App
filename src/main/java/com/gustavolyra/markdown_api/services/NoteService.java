package com.gustavolyra.markdown_api.services;

import com.gustavolyra.markdown_api.repositories.NoteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

   private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }















}
