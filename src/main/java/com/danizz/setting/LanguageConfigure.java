package com.danizz.setting;

import com.danizz.gui.TranslationConfigure;

public class LanguageConfigure {

    private TranslationConfigure settingsGui;
    private boolean isFromComboBoxModified;
    private boolean isToComboBoxModified;
    private PersistenceManager persistenceManager;

    public LanguageConfigure(TranslationConfigure settingsGui) {
        this.settingsGui = settingsGui;
        persistenceManager = PersistenceManager.getInstance();
        initLangComboBox();
    }

    public boolean isModified() {
        return isFromComboBoxModified || isToComboBoxModified;
    }

    private void initLangComboBox() {
        settingsGui.getLanguageFromComboBox().setSelectedItem(persistenceManager.getLanguageFrom());
        settingsGui.getLanguageToComboBox().setSelectedItem(persistenceManager.getLanguageTo());
        settingsGui.getLanguageFromComboBox().addActionListener(actionEvent -> isFromComboBoxModified = true);
        settingsGui.getLanguageToComboBox().addActionListener(actionEvent -> isToComboBoxModified = true);
    }

    public void save() {
        if (isFromComboBoxModified) {
            persistenceManager.setLanguageFrom((String) settingsGui.getLanguageFromComboBox().getSelectedItem());
            isFromComboBoxModified = false;
        }
        if (isToComboBoxModified) {
            persistenceManager.setLanguageTo((String) settingsGui.getLanguageToComboBox().getSelectedItem());
            isToComboBoxModified = false;
        }

    }
}
