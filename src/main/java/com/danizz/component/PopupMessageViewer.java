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

    private void createMessage(String text, JBColor color) {
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(
                        text,
                        null,
                        color,
                        null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(new RelativePoint(jComponent, point),
                        Balloon.Position.below);
    }

    public void showTranslatedText(String text) {
        createMessage(text, JBColor.LIGHT_GRAY);
    }

    public void showErrorMessage() {
        createMessage("Sorry, couldn't translate.", JBColor.RED);
    }
}
