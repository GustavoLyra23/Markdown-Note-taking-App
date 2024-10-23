package com.gustavolyra.markdown_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/markdown")
public class MarkDownController {

    @PostMapping("/grammar")
    public ResponseEntity<Void> check(@RequestParam("file") MultipartFile file) {


        return ResponseEntity.ok().build();
    }


}
