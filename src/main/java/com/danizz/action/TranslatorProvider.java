package com.danizz.action;

import com.danizz.PropertiesManager;
import com.danizz.translator.Translator;
import com.danizz.translator.impl.SimpleMultillectTranslator;
import com.danizz.translator.impl.SimpleYandexTranslator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

//TODO: implement updating key for Yandex Translator
public class TranslatorProvider {
    private Translator translator;

    private static TranslatorProvider ourInstance = new TranslatorProvider();

    public static TranslatorProvider getInstance() {
        return ourInstance;
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
