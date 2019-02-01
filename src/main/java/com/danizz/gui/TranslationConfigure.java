package com.danizz.gui;

import com.danizz.PropertiesManager;
import com.danizz.action.TranslatorProvider;
import com.danizz.translator.Translator;

import javax.swing.*;
import java.io.IOException;

//TODO: Separate logic from view (take logic out of this class)
public class TranslationConfigure {
    private JPanel root;
    private JTextField yandexAPIKey;
    private TranslatorProvider provider;
    private PropertiesManager propertiesManager;
    private Translator translator;

    public TranslationConfigure() {
        provider = TranslatorProvider.getInstance();
        translator = provider.getTranslator();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
    }

    public void updateApiKey() {
        try {
            if (translator.isApiKeyValid(yandexAPIKey.getText())) {
                propertiesManager.setProperty("saved.yandex.api-key", yandexAPIKey.getText());
                translator.setApiKey(yandexAPIKey.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isApiModified() {
        return !yandexAPIKey.getText().isEmpty();
    }

    public JPanel getRoot() {
        return root;
    }
}
