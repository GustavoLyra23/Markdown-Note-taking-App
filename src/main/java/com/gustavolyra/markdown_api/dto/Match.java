package com.gustavolyra.markdown_api.dto;

import java.util.Objects;

public record Match(String message, String sentence) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(message, match.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
