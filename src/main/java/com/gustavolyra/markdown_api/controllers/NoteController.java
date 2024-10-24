package com.gustavolyra.markdown_api.controllers;

import com.gustavolyra.markdown_api.dto.GrammarResponseDto;
import com.gustavolyra.markdown_api.dto.NoteDto;
import com.gustavolyra.markdown_api.services.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/markdowns")
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
        var html = noteService.convertToHtml(new String(file.getBytes()));
        return ResponseEntity.ok(html);
    }

    @GetMapping()
    public ResponseEntity<Page<NoteDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(noteService.findAll(pageable));
    }

    @PostMapping("/grammar-check")
    public ResponseEntity<GrammarResponseDto> checkGrammar(@RequestParam(defaultValue = "en-US") String language, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(noteService.checkGrammar(new String(file.getBytes()), language));
    }


}
