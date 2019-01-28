package com.danizz.setting;

import com.danizz.action.TranslatorProvider;
import com.danizz.gui.TranslationConfigure;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TranslatorConfigurable implements Configurable {
    private TranslationConfigure settingsGui;
    private TranslatorProvider provider;

    public TranslatorConfigurable() {
        provider = TranslatorProvider.getInstance();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Enable/Disable Translation by Double Click";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsGui = new TranslationConfigure();
        return settingsGui.getRoot();
    }

    @Override
    public boolean isModified() {
        return settingsGui.isTranslatorTypeModified() || settingsGui.isApiModified();
    }

    @Override
    public void apply() {
        settingsGui.selectTranslator();
        if (settingsGui.isApiModified())
            settingsGui.updateApiKey();
    }
}
