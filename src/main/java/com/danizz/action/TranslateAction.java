package com.danizz.action;

import com.danizz.component.PopupMessageViewer;
import com.danizz.component.TextSelector;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

import java.io.IOException;

public class TranslateAction extends AnAction implements DumbAware {

    private PopupMessageViewer messageViewer;
    private TextSelector textSelector;
    private TranslatorProvider provider = TranslatorProvider.getInstance();

    @Override
    public void actionPerformed(AnActionEvent e) {
        messageViewer = new PopupMessageViewer(e);
        textSelector = new TextSelector(e);
        String translatedMessage;
        try {
            translatedMessage = provider.getTranslator().translate(textSelector.getSelectedText());
        } catch (IOException e1) {
            translatedMessage = textSelector.getSelectedText();
            e1.printStackTrace();
        }
        messageViewer.showTranslatedText(translatedMessage);
    }

}