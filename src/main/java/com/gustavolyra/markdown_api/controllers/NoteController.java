package com.gustavolyra.markdown_api.controllers;

import com.gustavolyra.markdown_api.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/markdown")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/grammar")
    public ResponseEntity<Void> check(@RequestParam("file") MultipartFile file) {


        return ResponseEntity.ok().build();
    }


}