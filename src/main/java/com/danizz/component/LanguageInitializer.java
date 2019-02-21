package com.danizz.component;

import com.danizz.LanguageReductionExtractor;
import com.danizz.Trimmer;
import com.danizz.setting.PersistenceManager;
import com.danizz.translator.Translator;
import com.danizz.translator.TranslatorProvider;
import com.intellij.openapi.components.ApplicationComponent;

public class LanguageInitializer implements ApplicationComponent {

    private Translator translator;
    private PersistenceManager persistenceManager;
    private Trimmer trimmer;

    public LanguageInitializer() {
        trimmer = new LanguageReductionExtractor();
        translator = TranslatorProvider.getInstance().getTranslator();
        persistenceManager = PersistenceManager.getInstance();
        translator.setLanguageTo(trimmer.trim(persistenceManager.getLanguageTo()));
        translator.setLanguageFrom(trimmer.trim(persistenceManager.getLanguageFrom()));
    }
}
