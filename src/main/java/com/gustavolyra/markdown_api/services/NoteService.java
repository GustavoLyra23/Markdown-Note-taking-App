package com.gustavolyra.markdown_api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavolyra.markdown_api.dto.GrammarResponseDto;
import com.gustavolyra.markdown_api.dto.Match;
import com.gustavolyra.markdown_api.dto.NoteDto;
import com.gustavolyra.markdown_api.entities.Note;
import com.gustavolyra.markdown_api.exceptions.InvalidParamException;
import com.gustavolyra.markdown_api.repositories.NoteRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class NoteService {

   private final NoteRepository noteRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NoteService(NoteRepository noteRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.noteRepository = noteRepository;

        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void processAndSaveFile(MultipartFile file) throws IOException {
        verifyFileExtension(file.getOriginalFilename());
        String content = new String(file.getBytes());
        String html = renderMarkdownToHtml(content);
        noteRepository.save(Note.builder()
                .markDown(content.getBytes())
                .html(html.getBytes())
                .title(file.getOriginalFilename())
                .build());

    }

    public String renderMarkdownToHtml(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    @Transactional(readOnly = true)
    public Page<NoteDto> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable).map(NoteDto::new);
    }

    private void verifyFileExtension(String fileName) {
        if (fileName == null || !fileName.endsWith(".md")) {
            throw new IllegalArgumentException("Invalid file extension. Only .md files are allowed.");
        }
    }

    public String convertToHtml(String markdownContent) {
        verifyFileExtension(markdownContent);
        return renderMarkdownToHtml(markdownContent);
    }

    public GrammarResponseDto checkGrammar(String text, String language) {
        try {
            String url = "https://api.languagetool.org/v2/check";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("text", preprocessMarkdown(text));
            params.add("language", language);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                String detectedLanguage = jsonNode.get("language").get("name").asText();
                GrammarResponseDto grammarResponseDto = new GrammarResponseDto(detectedLanguage);
                jsonNode.get("matches").forEach(match -> {
                    String message = match.get("message").asText();
                    String sentence = match.get("sentence").asText();
                    grammarResponseDto.getMatches().add(new Match(message, sentence));
                });
                return grammarResponseDto;
            } else {
                throw new RuntimeException("Error while checking grammar");
            }
        } catch (Exception e) {
            throw new InvalidParamException(e.getMessage());
        }
    }
    private String preprocessMarkdown(String markdown) {
        return markdown
                .replaceAll("(?s)(```|~~~)[\\s\\S]*?\\1", "")
                .replaceAll("`[^`]+`", "")
                .replaceAll("\\*\\*|\\*|__|_", "")
                .replaceAll("(?m)^#{1,6}\\s*", "")
                .replaceAll("(?m)^\\s*[-+*]\\s+", "")
                .replaceAll("(?m)^\\d+\\.\\s+", "")
                .replaceAll("!?\\[.*?]\\(.*?\\)", "")
                .replaceAll("(?m)^>\\s*", "")
                .replaceAll("([-*]){3,}", "")
                .replaceAll("\\|.*?\\|", "")
                .replaceAll("<[^>]+>", "")
                .replaceAll("\\s{2,}", " ");
    }

}



