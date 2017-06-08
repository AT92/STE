package app.controller.view;

import app.controller.EditorController;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;


public class EditorView extends JFrame {
    protected JButton exitButton = new JButton("Programm beenden");
    protected JButton openButton = new JButton("Datei laden");
    public JButton saveButton = new JButton("Speichern");
    protected JButton saveAES = new JButton("Save file with AES");
    protected JButton openAES = new JButton("Open AES encrypted file");
    protected JTextArea textArea = new JTextArea(20, 100);

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
        toolBar.add(saveAES);
        toolBar.add(openAES);
        getContentPane().add(toolBar, BorderLayout.NORTH);
    }



    private void initEvents() {
        exitButton.addActionListener(al -> {
            controller.exit();
        });

        openButton.addActionListener(al -> {
            controller.openFile();
        });

        saveButton.addActionListener(al -> {
            controller.saveFile(textArea.getText());
        });

        saveAES.addActionListener(al -> {
            controller.saveAES();
        });

        openAES.addActionListener(al -> {
            Scanner scan = new Scanner(System.in);
            String s = scan.next();
            controller.openAES(s);
        });
    }

    public void setEditorContent(String content) {
        textArea.setText(content);
    }

    public String getEditorContent() {
        return textArea.getText();
    }
}
