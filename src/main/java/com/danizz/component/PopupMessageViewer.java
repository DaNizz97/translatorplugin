package com.danizz.component;

import com.danizz.translator.Translator;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PopupMessageViewer {
    private final JComponent jComponent;
    private final Point point;

    public PopupMessageViewer(AnActionEvent e) {
        TextSelector textSelector = new TextSelector(e);
        jComponent = textSelector.getCurrentComponent();
        point = textSelector.extractPoint();
    }

    private void showTranslatedText(String translatedText) {

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(
                        translatedText,
                        null,
                        JBColor.LIGHT_GRAY,
                        null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(new RelativePoint(jComponent, point),
                        Balloon.Position.below);
    }

    private void showErrorMessage() {
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(
                        "Sorry, couldn't translate:(",
                        null,
                        JBColor.RED,
                        null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(new RelativePoint(jComponent, point),
                        Balloon.Position.below);

    }

    public void showMessage(Translator translator, String selectedText) {
        try {
            showTranslatedText(translator.translate(selectedText));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage();
        }
    }
}
