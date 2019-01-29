package com.danizz.gui;

import com.danizz.PropertiesManager;
import com.danizz.action.TranslatorProvider;
import com.danizz.translator.impl.SimpleMultillectTranslator;
import com.danizz.translator.impl.SimpleYandexTranslator;

import javax.swing.*;

public class TranslationConfigure {
    private JPanel root;
    private JRadioButton yandexRadioButton;
    private JTextField yandexAPIKey;
    private TranslatorProvider provider;
    private PropertiesManager propertiesManager;

    public TranslationConfigure() {
        provider = TranslatorProvider.getInstance();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
    }

    public void selectTranslator() {
        if (yandexRadioButton.isSelected()) {
            provider.setTranslator(new SimpleYandexTranslator());
            propertiesManager.setProperty("saved.translator", "yandex");
        } else {
            provider.setTranslator(new SimpleMultillectTranslator());
            propertiesManager.setProperty("saved.translator", "multillect");
        }
    }

    public void updateApiKey() {
        propertiesManager.setProperty("saved.yandex.api-key", yandexAPIKey.getText());
        provider.setTranslator(new SimpleYandexTranslator());
    }

    public boolean isTranslatorTypeModified() {
        return (!(provider.getTranslator() instanceof SimpleYandexTranslator)
                ||
                !yandexRadioButton.isSelected())
                &&
                (!(provider.getTranslator() instanceof SimpleMultillectTranslator)
                        ||
                        yandexRadioButton.isSelected());
    }

    public boolean isApiModified() {
        return yandexRadioButton.isSelected() && !yandexAPIKey.getText().isEmpty();
    }

    public JPanel getRoot() {
        return root;
    }
}
