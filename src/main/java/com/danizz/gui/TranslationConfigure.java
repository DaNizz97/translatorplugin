package com.danizz.gui;

import javax.swing.*;

public class TranslationConfigure {
    private JPanel root;
    private JTextField yandexAPIKey;
    private JLabel incorrectApeLabel;

    public TranslationConfigure() {
        incorrectApeLabel.setVisible(false);
    }

    public JPanel getRoot() {
        return root;
    }

    public JTextField getYandexApiKey() {
        return yandexAPIKey;
    }

    public JLabel getIncorrectApeLabel() {
        return incorrectApeLabel;
    }
}
