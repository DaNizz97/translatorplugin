package com.danizz.setting;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "ConfigState",
        storages = {
                @Storage("ConfigState.xml")
        }
)
public class PersistenceManager implements PersistentStateComponent<PersistenceManager> {

    private String yandexApiKey;
    private String languageFrom = "Russian ( ru )";
    private String languageTo = "English ( en )";

    @Nullable
    @Override
    public PersistenceManager getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PersistenceManager state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }

    public String getYandexApiKey() {
        return yandexApiKey;
    }

    public void setYandexApiKey(String yandexApiKey) {
        this.yandexApiKey = yandexApiKey;
    }

    @Nullable
    public static PersistenceManager getInstance() {
        return ServiceManager.getService(PersistenceManager.class);
    }


}
