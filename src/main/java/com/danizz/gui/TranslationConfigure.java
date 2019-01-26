package com.danizz.gui;

import com.danizz.action.TranslatorProvider;
import com.danizz.translator.impl.SimpleMultillectTranslator;
import com.danizz.translator.impl.SimpleYandexTranslator;

import javax.swing.*;

public class TranslationConfigure {
    private JPanel root;
    private JRadioButton yandexRadioButton;
    private TranslatorProvider provider;

    public TranslationConfigure() {
        provider = TranslatorProvider.getInstance();
    }

    public void selectTranslator() {
        if (yandexRadioButton.isSelected()) {
            provider.setTranslator(new SimpleYandexTranslator());

        } else {
            provider.setTranslator(new SimpleMultillectTranslator());
        }
    }

    public boolean isModified() {
        return (!(provider.getTranslator() instanceof SimpleYandexTranslator)
                ||
                !yandexRadioButton.isSelected())
                &&
                (!(provider.getTranslator() instanceof SimpleMultillectTranslator)
                        ||
                        yandexRadioButton.isSelected());
    }

    public JPanel getRoot() {
        return root;
    }
}
