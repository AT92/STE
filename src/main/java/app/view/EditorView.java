package app.view;

import app.controller.EditorController;

import javax.swing.*;
import java.awt.*;
import java.io.File;


/**
 * This class stands for the view of the editor.
 * It creates the user interface of the editor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public class EditorView extends JFrame {
    /**
     * Elements of the view.
     */
    protected JButton exitButton = new JButton("Programm beenden");
    protected JButton openButton = new JButton("Load file");
    protected JButton saveButton = new JButton("Save file");
    protected JButton saveAES = new JButton("Save file with aes");
    protected JButton saveDES = new JButton("Save file with des");
    protected JButton openEncryptedFile = new JButton("Open encrypted file");
    protected JTextArea textArea = new JTextArea(20, 100);

    /**
     * The controller
     */
    private EditorController controller;

    /**
     * This is the constructor of the view.
     * It initializes all elements and the event handlers.
     *
     * @param controller, the controller of the view.
     */
    public EditorView(EditorController controller) {
        super("STE");
        this.controller = controller;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        getContentPane().add(mainPanel);
        mainPanel.add(textArea, BorderLayout.CENTER);
        initToolbar();
        initEvents();

        pack();
        setVisible(true);
    }

    /**
     * This method initializes the toolbar
     * and custom buttons and elements are added
     * to it.
     */
    private void initToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(openButton);
        toolBar.add(openEncryptedFile);
        toolBar.add(saveButton);
        toolBar.add(saveAES);
        toolBar.add(saveDES);
        toolBar.add(exitButton);
        getContentPane().add(toolBar, BorderLayout.NORTH);
    }


    /**
     * This method initializes the event handlers.
     */
    private void initEvents() {
        exitButton.addActionListener(al -> controller.exit());

        openButton.addActionListener(al -> textArea.setText(controller.openFile()));

        saveButton.addActionListener(al -> controller.saveFile(textArea.getText()));

        saveAES.addActionListener(al -> controller.saveAES());

        saveDES.addActionListener(al -> controller.saveDES());

        openEncryptedFile.addActionListener(al -> textArea.setText(controller.openEncryptedFile()));



    }

    /**
     * This method creates an input dialog for the user.
     * @param text, the text, which informs the user about the input, he should give.
     * @return the input, which the user gave.
     */
    public String askForInput(String text) {
        return JOptionPane.showInputDialog(text);
    }

    /**
     * This method create an information dialog for the user.
     * @param text, the text, which informs the user.
     */
    public void informUser(String text) {
        JTextArea message = new JTextArea(text);
        message.setEditable(false);
        JOptionPane.showMessageDialog(null, message, "Important informaton!", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method creates a new options dialog with the passed options.
     * @param options, the options, between which the user have to choose.
     * @param title, the title of the dialog.
     * @param message, the message of the dialog.
     * @return the selected option as a string.
     */
    public String createOptionDialog(String[] options, String title, String message) {
        int option = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        return options[option];
    }

    /**
     * This method sets the passed content to the textarea of the editor.
     * @param content, the content, which should be in the textarea.
     */
    public void setEditorContent(String content) {
        textArea.setText(content);
    }

    /**
     * This method gets the content out of the textarea.
     * @return the content of the textarea.
     */
    public String getEditorContent() {
        return textArea.getText();
    }



    public File selectFile(boolean forSave) {
        JFileChooser fileChooser = new JFileChooser();
        if (forSave) {
            fileChooser.showSaveDialog(null);
            if (fileChooser.getSelectedFile() != null) {
                return fileChooser.getSelectedFile();
            }
        } else {
            fileChooser.showOpenDialog(null);
            if (fileChooser.getSelectedFile() != null) {
                return fileChooser.getSelectedFile();
            }
        }
        return null;
    }
}