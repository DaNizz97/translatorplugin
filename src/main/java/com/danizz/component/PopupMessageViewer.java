package com.danizz.component;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;
import java.awt.*;

public class PopupMessageViewer {
    private final JComponent jComponent;
    private final Point point;

    public PopupMessageViewer(AnActionEvent e) {
        TextSelector textSelector = new TextSelector(e);
        jComponent = textSelector.getCurrentComponent();
        point = textSelector.extractPoint();
    }

    public void showTranslatedText(String translatedText) {

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(
                        translatedText,
                        null,
                        JBColor.GRAY,
                        null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(new RelativePoint(jComponent, point),
                        Balloon.Position.below);
    }
}
