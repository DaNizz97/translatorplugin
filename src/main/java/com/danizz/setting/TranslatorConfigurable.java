package com.danizz.setting;

import com.danizz.setting.gui.TranslationConfigure;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TranslatorConfigurable implements Configurable {
    private TranslationConfigure settingsGui;
    private LanguageConfigure languageConfigure;
    private ApiKeyConfigure apiKeyConfigure;

    public TranslatorConfigurable() {
        settingsGui = new TranslationConfigure();
        apiKeyConfigure = new ApiKeyConfigure(settingsGui);
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
        return apiKeyConfigure.isModified() || languageConfigure.isModified();
    }

    @Override
    public void apply() {
        if (apiKeyConfigure.isModified()) {
            apiKeyConfigure.updateApiKey();
        }
        if (languageConfigure.isModified()) {
            languageConfigure.save();
        }
    }
}
