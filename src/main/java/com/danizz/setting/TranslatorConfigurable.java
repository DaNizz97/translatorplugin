package com.danizz.setting;

import com.danizz.PropertiesManager;
import com.danizz.action.TranslatorProvider;
import com.danizz.gui.TranslationConfigure;
import com.danizz.translator.Translator;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TranslatorConfigurable implements Configurable {
    private TranslationConfigure settingsGui;
    private PropertiesManager propertiesManager;
    private TranslatorProvider provider;
    private Translator translator;
    private JTextField yandexApiKey;
    private boolean isModified;
    private JLabel incorrectApeLabel;

    public TranslatorConfigurable() {
        settingsGui = new TranslationConfigure();
        provider = TranslatorProvider.getInstance();
        translator = provider.getTranslator();
        propertiesManager = new PropertiesManager("/home/da-nizz/IdeaProjects/TranslatorPlugin/src/main/resources/config.properties");
        incorrectApeLabel = settingsGui.getIncorrectApeLabel();
        initializeYandexApiKeyTextField();
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
        return isModified;
    }

    @Override
    public void apply() {
        updateApiKey();
    }

    private void updateApiKey() {
        try {
            if (translator.isApiKeyValid(yandexApiKey.getText())) {
                propertiesManager.setProperty("yandex.api-key", yandexApiKey.getText());
                translator.setApiKey(yandexApiKey.getText());
                incorrectApeLabel.setVisible(false);
                yandexApiKey.setText("");
                setModified(true);
            } else {
                incorrectApeLabel.setText("Sorry, your API Key is not valid.");
                incorrectApeLabel.setVisible(true);
                setModified(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException || e instanceof SocketException) {
                incorrectApeLabel.setText("Sorry, you can't change API Key without internet connection.");
                incorrectApeLabel.setVisible(true);
            }
        }
    }

    private void initializeYandexApiKeyTextField() {
        yandexApiKey = settingsGui.getYandexApiKeyTextField();
        yandexApiKey.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLable();

            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLable();

            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLable();
            }

            private void hideIncorrectApiLable() {
                settingsGui.getIncorrectApeLabel().setVisible(false);
                setModified(true);
            }
        });
    }

    private void setModified(boolean b) {
        this.isModified = b;
    }
}
