package com.danizz.translator.impl;

import com.danizz.translator.Translator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SimpleYandexTranslatorTest {

    private Translator translator;
    @Before
    public void setUp() {
        translator = new SimpleYandexTranslator();
    }

    @Test
    public void translateStringInCamelCaseFromEnglish() throws IOException {
        String stringInCamelCase = "StringForTranslation";
        assertEquals("Строки Для Перевода", translator.translate(stringInCamelCase));
    }

    @Test
    public void translateStringInSnakelCaseFromEnglish() throws IOException {
        String stringInCamelCase = "string_for_translation";
        assertEquals("строки для перевода", translator.translate(stringInCamelCase));
    }

    @Test
    public void translateStringFromRussian() throws IOException {
        String stringInCamelCase = "Строка для перевода";
        assertEquals("The string to translate", translator.translate(stringInCamelCase));
    }
}