package com.gustavolyra.markdown_api.services;

import com.gustavolyra.markdown_api.entities.Note;
import com.gustavolyra.markdown_api.repositories.NoteRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NoteService {

   private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void processAndSaveFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        String html = renderMarkdownToHtml(content);
        noteRepository.save(Note.builder().markDown(content.getBytes()).html(html.getBytes()).build());

    }

    public String renderMarkdownToHtml(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
