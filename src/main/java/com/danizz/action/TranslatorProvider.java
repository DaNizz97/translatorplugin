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
    private PropertiesManager propertiesManager;

    private static TranslatorProvider ourInstance = new TranslatorProvider();

    public static TranslatorProvider getInstance() {
        return ourInstance;
    }

    private TranslatorProvider() {
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
        this.translator = initTranslatorFromProperties();
    }

    @NotNull
    @Contract(" -> new")
    private Translator initTranslatorFromProperties() {
        String savedTranslatorProperty = "saved.translator";
        if(propertiesManager.containsKey(savedTranslatorProperty)) {
            String propertyValue = propertiesManager.getProperties(savedTranslatorProperty);
            if (propertyValue.equals("yandex")) {
                return new SimpleYandexTranslator();
            } else if (propertyValue.equals("multillect")) {
                return new SimpleMultillectTranslator();
            }
        }
        return new SimpleYandexTranslator();
    }

    public Translator setTranslator(Translator translator) {
        return this.translator = translator;
    }

    public Translator getTranslator() {
        return translator;
    }
}
