package app.view;

import app.controller.EditorController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.InvalidParameterException;


/**
 * This class stands for the view of the editor.
 * It creates the user interface of the editor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 */
public final class EditorView extends JFrame {
    /**
     * Button for terminating the program.
     */
    private final JButton exitButton = new JButton("Programm beenden");
    /**
     * Button for opening a file.
     */
    private final JButton openButton = new JButton("Load file");
    /**
     * Button for saving content to file.
     */
    private final JButton saveButton = new JButton("Save file");
    /**
     * Button for saving encrypted as AES.
     */
    private final JButton saveAES = new JButton("Save file with aes");
    /**
     * Button for saving encrypted as DES.
     */
    private final JButton saveDES = new JButton("Save file with des");
    /**
     * Button for saving encrypted password based.
     */
    private final JButton savePBE = new JButton("Save file with PBE");
    /**
     * Button for saving encrypted as RSA.
     */
    private final JButton saveRSA = new JButton("Save file with RSA");
    /**
     * Button for generating the pair of RSA keys.
     */
    private final JButton genRSAKeys = new JButton("Generate RSA Keys");
    /**
     * Button for opening an encrypted file.
     */
    private final JButton openEncryptedFile = new JButton("Open encrypted file");
    /**
     * The text area of the secure text editor.
     */
    private final JTextArea textArea = new JTextArea(20, 100);

    /**
     * The controller of the view.
     */
    private final EditorController controller;

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
        //making the textarea responsive and streachable
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
        toolBar.add(savePBE);
        toolBar.add(saveRSA);
        toolBar.add(genRSAKeys);
        toolBar.add(exitButton);
        getContentPane().add(toolBar, BorderLayout.NORTH);
    }


    /**
     * This method initializes the event handlers. It connects the view elements to the controller methods.
     */
    private void initEvents() {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        exitButton.addActionListener(al -> controller.exit());

        openButton.addActionListener(al -> {
            try {
                textArea.setText(controller.openFile());
            } catch (NullPointerException n) {
                //do nothing
            }
        });


        saveButton.addActionListener(al -> controller.saveFile(textArea.getText()));

        saveAES.addActionListener(al -> controller.saveAES());

        saveDES.addActionListener(al -> controller.saveDES());

        savePBE.addActionListener(al -> controller.savePBE());

        openEncryptedFile.addActionListener(al -> textArea.setText(controller.openEncryptedFile()));

        saveRSA.addActionListener(al -> controller.saveRSA());

        genRSAKeys.addActionListener(al -> controller.generateRSAKeys());

    }

    /**
     * This method creates an input dialog for the user, and asking for giving a password.
     * If the passowrd appears for encryption, this method checks whether it is a strong password,
     * If the password is weak, the user should pass a strong password.
     * If the password should be used for decryption, it wull not be proofed for security reasons,
     * so the attacker may not know which regex to use.
     *
     * @param forEncryption, true if the password should be used for encryption, false if for decryption.
     * @return the password, which the user gave in.
     */
    public String askForPassword(boolean forEncryption) {
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter strong Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //The case, when the dialog was not canceled.
        if (okCxl == JOptionPane.OK_OPTION) {
            String pass = new String(pf.getPassword());
            //Checking the regex of the password, and asking for a new one, if it is weak.
            if (pass.length() > 8 && pass.length() < 32
                    && (pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") || !forEncryption)) {
                return pass;
            } else {
                return askForPassword(forEncryption);
            }
        //The case, when the user cancel the dialog.
        } else {
            throw new InvalidParameterException("Ach, schade :(");
        }
    }

    /**
     * This method create an information dialog for the user.
     * @param text, the text, which informs the user.
     */
    public void informUser(String text) {
        JOptionPane.showMessageDialog(null, text, "Important informaton!", JOptionPane.INFORMATION_MESSAGE);
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
     * This method gets the content out of the textarea.
     * @return the content of the textarea.
     */
    public String getEditorContent() {
        return textArea.getText();
    }


    /**
     * This method opens the filechooser for picking any file.
     * @param forSave, is set to true, when a file should be saved, and set to false if a file should be opened.
     * @return the file which was picked by the user.
     */
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
