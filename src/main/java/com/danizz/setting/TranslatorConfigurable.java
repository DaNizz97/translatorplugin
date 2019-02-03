package com.danizz.setting;

import com.danizz.PropertiesManager;
import com.danizz.action.TranslatorProvider;
import com.danizz.gui.TranslationConfigure;
import com.danizz.translator.Translator;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

public class TranslatorConfigurable implements Configurable {
    private TranslationConfigure settingsGui;
    private PropertiesManager propertiesManager;
    private TranslatorProvider provider;
    private Translator translator;
    private JTextField yandexApiKey;
    private boolean isEnteredKeyValid = true;
    private String previousApiValue;

    public TranslatorConfigurable() {
        settingsGui = new TranslationConfigure();
        provider = TranslatorProvider.getInstance();
        translator = provider.getTranslator();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
        yandexApiKey = settingsGui.getYandexApiKey();
        previousApiValue = propertiesManager.getProperties("yandex.api-key");
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Translation Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return settingsGui.getRoot();
    }

    @Override
    public boolean isModified() {
        return isEnteredKeyValid || isModified(settingsGui.getYandexApiKey(), previousApiValue);
    }

    @Override
    public void apply() {
        previousApiValue = yandexApiKey.getText();
        try {
            if (translator.isApiKeyValid(yandexApiKey.getText())) {
                updateApiKey();
                settingsGui.getIncorrectApeLabel().setVisible(false);
                yandexApiKey.setText("");
            } else {
                settingsGui.getIncorrectApeLabel().setVisible(true);
                isEnteredKeyValid = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateApiKey() {
        propertiesManager.setProperty("yandex.api-key", yandexApiKey.getText());
        translator.setApiKey(yandexApiKey.getText());
    }
}
