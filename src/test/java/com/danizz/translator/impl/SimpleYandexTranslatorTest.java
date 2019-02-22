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
        translator.setLanguageFrom("ru");
        translator.setLanguageTo("en");
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

    @Test
    public void isApiKeyValidTest() throws  IOException{
        String apiKey = "trnsl.1.1.20190104T101334Z.58a19d25ba02f8b6.b11842436bbf4e1997e4dd963bb66413942b40d9";
        assertTrue(translator.isApiKeyValid(apiKey));
    }

    @Test
    public void isApiKeyNotValidTest() throws  IOException{
        String apiKey = "not_valid_api_key";
        assertFalse(translator.isApiKeyValid(apiKey));
    }

    @Test
    public void translate() throws IOException {
        translator.translate("Hello");
    }
}