package com.danizz.gui;

import javax.swing.*;

public class TranslationConfigure {
    private JPanel root;
    private JTextField yandexAPIKeyTextField;
    private JLabel incorrectApiLabel;
    private JComboBox langSet;

    public TranslationConfigure() {
        incorrectApiLabel.setVisible(false);
    }

    public JPanel getRoot() {
        return root;
    }

    public JTextField getYandexApiKeyTextField() {
        return yandexAPIKeyTextField;
    }

    public JLabel getIncorrectApiLabel() {
        return incorrectApiLabel;
    }

    public JComboBox getLangSet() {
        return langSet;
    }
}
