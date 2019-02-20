package com.danizz.translator;

import com.danizz.translator.impl.SimpleYandexTranslator;

public class TranslatorProvider {
    private Translator translator;

    private static TranslatorProvider instance = new TranslatorProvider();

    public static TranslatorProvider getInstance() {
        return instance;
    }

    private TranslatorProvider() {
        this.translator = new SimpleYandexTranslator();
    }

    public Translator setTranslator(Translator translator) {
        return this.translator = translator;
    }

    public Translator getTranslator() {
        return translator;
    }
}
