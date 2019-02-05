package com.danizz.gui;

import javax.swing.*;

public class TranslationConfigure {
    private JPanel root;
    private JTextField yandexAPIKeyTextField;
    private JLabel incorrectApeLabel;

    public TranslationConfigure() {
        incorrectApeLabel.setVisible(false);
    }

    public JPanel getRoot() {
        return root;
    }

    public JTextField getYandexApiKeyTextField() {
        return yandexAPIKeyTextField;
    }

    public JLabel getIncorrectApeLabel() {
        return incorrectApeLabel;
    }
}
