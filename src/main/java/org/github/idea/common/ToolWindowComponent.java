package org.github.idea.common;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This class represents common behavior of tool window component.
 *
 * @author Alexander Shvets
 * @version 1.0 11/24/2007
 */
public abstract class ToolWindowComponent {
  private static final Runnable EMPTY_RUNNABLE = new Runnable() {
    public void run() {
    }
  };

  private IdeaHelper ideaHelper = new IdeaHelper();

  private boolean isRegistered = false;

  private JPanel mainPanel;
  protected JPanel contentPanel;

  private Project project;
  private String toolWindowId;

  public ToolWindowComponent(Project project, String toolWindowId) {
    this.project = project;
    this.toolWindowId = toolWindowId;
  }

  protected void create() {
    createMainPanel();

    createContentPanel();

    mainPanel.add(contentPanel, BorderLayout.CENTER);

    ToolTipManager.sharedInstance().registerComponent(mainPanel);
  }

  private void createMainPanel() {
    mainPanel = new JPanel(new BorderLayout());
  }

  protected void createContentPanel() {
    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
  }

  protected void dispose() {
    if (mainPanel != null) {
      ToolTipManager.sharedInstance().unregisterComponent(mainPanel);
    }

    contentPanel = null;
    mainPanel = null;
  }

  private void initMainPanel() {
    mainPanel.add(createToolbar(), BorderLayout.NORTH);
  }

  protected void initContentPanel() {
    contentPanel.removeAll();

    JScrollPane scrollPane = new JBScrollPane();

    contentPanel.add(scrollPane, BorderLayout.CENTER);

    contentPanel.repaint();
  }

  protected void createConsole() {
    createToolWindow();

    initMainPanel();

    initContentPanel();

    setRegistered(true);
  }

  public void closeConsole() {
    setConsoleVisible(false);

    dispose();

    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

    try {
      toolWindowManager.unregisterToolWindow(toolWindowId);
    }
    catch (IllegalArgumentException e) {
      // ignore - this can occur due to lazy initialization
    }

    setRegistered(false);
  }

  public void setConsoleVisible(boolean isVisible) {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

    ToolWindow toolWindow = toolWindowManager.getToolWindow(toolWindowId);

    if (toolWindow != null && isRegistered) {
      if (isVisible) {
        toolWindow.show(EMPTY_RUNNABLE);
      } else if (toolWindow.isVisible()) {
        toolWindow.hide(EMPTY_RUNNABLE);
      }
    }
  }

  private ToolWindow createToolWindow() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

    ToolWindow toolWindow = toolWindowManager.getToolWindow(toolWindowId);

    if (toolWindow == null) {
      toolWindow = ideaHelper.createToolWindow(project, mainPanel, toolWindowId, ToolWindowAnchor.LEFT, null);

      customizeToolWindow(toolWindow);
    }

    return toolWindow;
  }

  protected void customizeToolWindow(ToolWindow toolWindow) {
  }

  protected abstract JComponent createToolbar();

  protected ToolWindow getToolWindow() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

    return toolWindowManager.getToolWindow(toolWindowId);
  }

  protected boolean isRegistered() {
    return isRegistered;
  }

  private void setRegistered(boolean registered) {
    isRegistered = registered;
  }

  public Project getProject() {
    return project;
  }

  protected JPanel getMainPanel() {
    return mainPanel;
  }

  protected JPanel getContentPanel() {
    return contentPanel;
  }

}
