package com.danizz.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.Messages;

public class TranslateAction extends AnAction implements DumbAware {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showMessageDialog("First Action Example", "Example", Messages.getInformationIcon());
    }
}
