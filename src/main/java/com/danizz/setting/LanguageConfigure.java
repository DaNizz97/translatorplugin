package com.danizz.setting;

import com.danizz.LanguageReductionExtractor;
import com.danizz.Trimmer;
import com.danizz.setting.gui.TranslationConfigure;
import com.danizz.translator.TranslatorProvider;

import javax.swing.*;

public class LanguageConfigure {

    private TranslationConfigure settingsGui;
    private boolean isFromComboBoxModified;
    private boolean isToComboBoxModified;
    private PersistenceManager persistenceManager;
    private TranslatorProvider translatorProvider;
    private Trimmer trimmer;

    public LanguageConfigure(TranslationConfigure settingsGui) {
        this.settingsGui = settingsGui;
        persistenceManager = PersistenceManager.getInstance();
        translatorProvider = TranslatorProvider.getInstance();
        trimmer = new LanguageReductionExtractor();
        initLangComboBox();

    }

    public boolean isModified() {
        return isFromComboBoxModified || isToComboBoxModified;
    }

    private void initLangComboBox() {
        JComboBox languageFromComboBox = settingsGui.getLanguageFromComboBox();
        languageFromComboBox.setSelectedItem(persistenceManager.getLanguageFrom());
        JComboBox languageToComboBox = settingsGui.getLanguageToComboBox();
        languageToComboBox.setSelectedItem(persistenceManager.getLanguageTo());
        languageFromComboBox.addActionListener(actionEvent -> isFromComboBoxModified = true);
        languageToComboBox.addActionListener(actionEvent -> isToComboBoxModified = true);
    }

    public void save() {
        if (isFromComboBoxModified) {
            String langFromString = (String) settingsGui.getLanguageFromComboBox().getSelectedItem();
            persistenceManager.setLanguageFrom(langFromString);
            translatorProvider.getTranslator().setLanguageFrom(trimmer.trim(langFromString));
            isFromComboBoxModified = false;
        }
        if (isToComboBoxModified) {
            String langToString = (String) settingsGui.getLanguageToComboBox().getSelectedItem();
            persistenceManager.setLanguageTo(langToString);
            translatorProvider.getTranslator().setLanguageTo(trimmer.trim(langToString));
            isToComboBoxModified = false;
        }
    }
}
