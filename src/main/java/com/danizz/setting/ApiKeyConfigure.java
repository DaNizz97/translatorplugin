package com.danizz.setting;

import com.danizz.setting.gui.TranslationConfigure;
import com.danizz.translator.Translator;
import com.danizz.translator.TranslatorProvider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ApiKeyConfigure {

    private boolean isModified;
    private TranslationConfigure settingsGui;
    private Translator translator;
    private JTextField yandexApiKeyTextField;
    private JLabel incorrectApiKeyLabel;
    private PersistenceManager persistenceManager;

    public ApiKeyConfigure(TranslationConfigure settingsGui) {
        this.settingsGui = settingsGui;
        translator = TranslatorProvider.getInstance().getTranslator();
        incorrectApiKeyLabel = settingsGui.getIncorrectApiLabel();
        initYandexApiKeyTextField();
        persistenceManager = PersistenceManager.getInstance();
    }

    public boolean isModified() {
        return isModified;
    }

    public void updateApiKey() {
        try {
            String apiKey = yandexApiKeyTextField.getText();
            if (translator.isApiKeyValid(apiKey)) {
                persistenceManager.setYandexApiKey(apiKey);
                translator.setApiKey(apiKey);
                incorrectApiKeyLabel.setVisible(false);
                yandexApiKeyTextField.setText("");
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
        yandexApiKeyTextField = settingsGui.getYandexApiKeyTextField();
        yandexApiKeyTextField.getDocument().addDocumentListener(new DocumentListener() {
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
