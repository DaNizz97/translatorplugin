package com.danizz.setting;

import com.danizz.PropertiesManager;
import com.danizz.gui.TranslationConfigure;
import com.danizz.translator.Translator;
import com.danizz.translator.TranslatorProvider;
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
    private JLabel incorrectApiKeyLabel;
    private LanguageConfigure languageConfigure;

    public TranslatorConfigurable() {
        settingsGui = new TranslationConfigure();
        provider = TranslatorProvider.getInstance();
        translator = provider.getTranslator();
        propertiesManager = new PropertiesManager("/config.properties");
        incorrectApiKeyLabel = settingsGui.getIncorrectApiLabel();
        initYandexApiKeyTextField();
        languageConfigure = new LanguageConfigure(settingsGui);
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
        return isModified || languageConfigure.isModified();
    }

    @Override
    public void apply() {
        if (isModified) {
            updateApiKey();
        }
        if (languageConfigure.isModified()) {
            languageConfigure.save();
        }
    }

    private void updateApiKey() {
        try {
            if (translator.isApiKeyValid(yandexApiKey.getText())) {
                propertiesManager.setProperty("yandex.api-key", yandexApiKey.getText());
                translator.setApiKey(yandexApiKey.getText());
                incorrectApiKeyLabel.setVisible(false);
                yandexApiKey.setText("");
                setModified(true);
            } else {
                setIncorrectApiKeyLabelVisible("Sorry, your API Key is not valid.");
                setModified(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException || e instanceof SocketException) {
                setIncorrectApiKeyLabelVisible("Sorry, you can't change API Key without internet connection.");
            }
        }
    }

    private void initYandexApiKeyTextField() {
        yandexApiKey = settingsGui.getYandexApiKeyTextField();
        yandexApiKey.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLabel();

            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLabel();

            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                hideIncorrectApiLabel();
            }

            private void hideIncorrectApiLabel() {
                settingsGui.getIncorrectApiLabel().setVisible(false);
                setModified(true);
            }
        });
    }

    private void setModified(boolean b) {
        this.isModified = b;
    }

    private void setIncorrectApiKeyLabelVisible(String message) {
        incorrectApiKeyLabel.setText(message);
        incorrectApiKeyLabel.setVisible(true);
    }
}
