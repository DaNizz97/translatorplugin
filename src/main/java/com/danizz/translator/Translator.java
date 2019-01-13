package com.danizz.translator;

import java.io.IOException;

public interface Translator {
    String translate(String lang, String input) throws IOException;

    String translate(String input) throws IOException;
}
