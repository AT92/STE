package app.controller;

import app.data.FileManager;
import app.controller.view.EditorView;
import app.logic.AES.CBC.AESCBCDecryptor;
import app.logic.AES.CBC.AESCBCEncryptor;
import app.logic.AES.ECB.AESECBDecryptor;
import app.logic.AES.ECB.AESECBEncryptor;
import app.logic.DES.ECB.DESECBDecryptor;
import app.logic.DES.ECB.DESECBEncryptor;
import app.logic.Decryptor;
import app.logic.Encryptor;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is the controller of the editor.
 * It delegate user actions to the write methods and functions.
 * It informs user and controls the view according to the binded logic layer.
 *
 * @author Anfrej Tihonov
 * @version 1.0
 */
public class EditorController {
    /**
     * The view of the tex teditor.
     */
    private EditorView view;

    /**
     * This is the constructor of this class.
     * It creates the view and binds it to this object.
     */
    public EditorController() {
        this.view = new EditorView(this);
    }

    /**
     * This method terminates the program.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * This method opens a file, reads it content out.
     *
     * @return the content of the file, which was opened.
     */
    public String openFile() {
        try {
            FileManager contentReader = new FileManager(view.selectFile(false));
            return contentReader.getContentOutOfFile();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei wurde nicht gefunden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gelesen werden!");
        } catch (NullPointerException n) {
            return "";
        }
        return "";
    }

    /**
     * This method saves the passed content to the file.
     *
     * @param content, the content, which should be saved to the file.
     * @return true if the content was successfully saved, false otherwise.
     */
    public boolean saveFile(String content) {
        try {
            FileManager contentWriter = new FileManager(view.selectFile(true));
            contentWriter.writeContentToFile(content);
            return true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gespeichert werden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Es ist ein Fehler bei dem Schreiben der Datei passiert!");
        } catch (NullPointerException n) {
            return false;
        }
        return false;
    }

    /**
     * This method lets the user to choose between three padding options.
     * @return the chosen padding.
     */
    private String choosePadding() {
        String[] paddingOptions = new String[] {"No Padding", "PKCS7Padding", "ZeroBytePadding"};
        try {
            return view.createOptionDialog(paddingOptions, "Padding selection", "Select the padding mechanism!");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method lets the user to choose between three key sizes for the AES Encryption.
     * @return the size of the AES Key.
     */
    private int chooseKeysize() {
        String[] options = new String[] {"128 Bit", "192 Bit", "256 Bit"};
        String option = view.createOptionDialog(options, "Keysize selection", "Select the AES keysize!");
        return Integer.parseInt(option.substring(0, 3));
    }

    /**
     * This method lets the user to choose between three block modi for the encryption.
     * @return the selected block modi.
     */
    private String chooseBlockModus() {
        String[] options = new String[] {"ECB", "CBC"};
        return view.createOptionDialog(options, "Block modus selection", "Select the block modus");
    }

    public void saveAES() {
        String padding = choosePadding();
        if (padding.equals("NoPadding") && view.getEditorContent().length() % 16 != 0) {
            JOptionPane.showMessageDialog(null, "The textsize must be a multiple of 16 for this encryption!");
            return;
        }
        try {
            String blockMode = chooseBlockModus();
            int keyLength = chooseKeysize();
            Encryptor enc;
            switch (blockMode) {
                case "ECB": enc = new AESECBEncryptor(keyLength, padding);
                            break;
                case "CBC": enc = new AESCBCEncryptor(keyLength, padding);
                            break;
                default: throw new IllegalArgumentException();
            }
            String encContent = enc.encryptAsString(view.getEditorContent());
            if (saveFile(encContent)) {
                view.informUser("This is the " + keyLength + " bit long key for this document:\n" + enc.getKeyAsString());
            } else {
                view.informUser("The file could not be saved!");
            }
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
    }

    public String openAES() {
        String encContent = openFile();
        try {
            String key = view.askForInput("Please input the key for this file below:");
            String blockMode = chooseBlockModus();
            String padding = choosePadding();
            Decryptor decryptor;
            switch (blockMode) {
                case "ECB": decryptor = new AESECBDecryptor(padding, key);
                    break;
                case "CBC": decryptor = new AESCBCDecryptor(padding, key);
                    break;
                default: throw new IllegalArgumentException();
            }
            return decryptor.decryptAsString(encContent);
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
        return "";
    }

    public void saveDES() {
        String padding = choosePadding();
        if (padding.equals("NoPadding") && view.getEditorContent().length() % 8 != 0) {
            view.informUser("The textsize must be a multiple of 8 for this encryption!");
            return;
        }
        try {
            Encryptor enc = new DESECBEncryptor(padding);
            String encContent = enc.encryptAsString(view.getEditorContent());
            if (saveFile(encContent)) {
                view.informUser("This is the key for this document:\n" + enc.getKeyAsString());
            }
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
    }

    public String openDES() {
        String encContent = openFile();
        String key = view.askForInput("Please input the key for this file below:");
        try {
            Decryptor decryptor = new DESECBDecryptor(choosePadding(), key);
            return decryptor.decryptAsString(encContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
