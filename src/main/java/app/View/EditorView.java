package app.View;

import app.Controller.EditorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;


public class EditorView extends JFrame {
    protected JButton exitButton = new JButton("Exit");
    protected JButton openButton = new JButton("Open File");
    protected JButton saveButton = new JButton("Save File");
    private JTextArea textArea = new JTextArea(50, 150);

    private EditorController controller;

    public EditorView(EditorController controller) {
        super("STE");
        this.controller = controller;
        JPanel mainPanel = new JPanel();

        getContentPane().add(mainPanel);
        mainPanel.add(textArea);
        initToolbar();

        initEvents();

        pack();
        setVisible(true);
    }

    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.add(exitButton);
        getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    private void initEvents() {
        exitButton.addActionListener(al -> {
            controller.exit();
        });

        openButton.addActionListener(al -> {
            controller.openFile();
        });
    }
}
