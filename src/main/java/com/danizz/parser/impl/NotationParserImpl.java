package com.danizz.parser.impl;

import com.danizz.parser.NotationParser;

public class NotationParserImpl implements NotationParser {
    @Override
    public String parseCamelNotation(String input) {
        return input.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    @Override
    public String parseSnakeNotation(String input) {
        return input.replaceAll("_", " ");
    }
}
