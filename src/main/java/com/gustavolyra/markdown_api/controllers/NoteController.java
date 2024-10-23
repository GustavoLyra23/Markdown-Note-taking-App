package com.gustavolyra.markdown_api.controllers;

import com.gustavolyra.markdown_api.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/markdown")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/upload")
    public ResponseEntity<String> check(@RequestParam("file") MultipartFile file) throws IOException {
        noteService.processAndSaveFile(file);
        return ResponseEntity.ok("File saved successfully");
    }

    @PostMapping("/html-convert")
    public ResponseEntity<String> convertToHtml(@RequestParam("file") MultipartFile file) throws IOException {
        var html = noteService.renderMarkdownToHtml(new String(file.getBytes()));
        return ResponseEntity.ok(html);
    }


}
