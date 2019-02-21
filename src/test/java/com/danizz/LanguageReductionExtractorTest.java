package com.danizz;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageReductionExtractorTest {

    private Trimmer trimmer;
    @Before
    public void setUp() throws Exception {
        trimmer = new LanguageReductionExtractor();
    }

    @Test
    public void trim() {
        String lang = "Russian ( ru )";
        String shortLang = trimmer.trim(lang);

        assertEquals("ru", shortLang);
    }
}