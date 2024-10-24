package com.gustavolyra.markdown_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


@Getter
@AllArgsConstructor
public class GrammarResponseDto {

    private final String detectedLanguage;
    private final Set<Match> matches = new HashSet<>();


}
