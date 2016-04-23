package org.github.cafebabe.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

import org.github.idea.common.IdeaAction;
import org.sf.cafebabe.util.IconProducer;
import org.sf.cafebabe.Constants;

/**
 * This action displays information about the plugin.
 *
 * @author Alexander Shvets
 * @version 1.0 11/24/2007
 */
public class AboutAction extends IdeaAction {

  public void actionPerformed(AnActionEvent event) {
    Project project = helper.getProject(event);

    String message = Constants.MAIN_FRAME_TITLE + '\n' +
        "Author: Alexander Shvets" + '\n' +
        "e-mail: <alexander.shvets@gmail.com>" + '\n';

    final ImageIcon icon = IconProducer.getImageIcon(Constants.ICON_FACE1);    

    Messages.showMessageDialog(project, message, "About CafeBabe", icon);
  }
}