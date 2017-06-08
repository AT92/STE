package app.controller;

import app.data.FileManager;
import app.controller.view.EditorView;
import app.logic.AES.AESDecrypto;
import app.logic.AES.AESEncrypto;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class EditorController {
    private EditorView view;

    public EditorController() {
        this.view = new EditorView(this);
    }


    public void exit() {
        System.exit(0);
    }


    public String openFile() {
        try {
            FileManager contentReader = new FileManager(selectFile(false));
            return contentReader.getContentOutOfFile();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei wurde nicht gefunden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gelesen werden!");
        } catch (NullPointerException n) {

        }
        return "";
    }

    public boolean saveFile(String content) {
        try {
            FileManager contentWriter = new FileManager(selectFile(true));
            contentWriter.writeContentToFile(content);
            return true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gespeichert werden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Es ist ein Fehler bei dem Schreiben der Datei passiert!");
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        return false;
    }

    public void saveAES() {
        try {
            String[] options = new String[] {"128 Bit", "192 Bit", "256 Bit"};
            int option = JOptionPane.showOptionDialog(null, "Select the AES key size!", "Keysize selection",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
            int keyLength;
            switch (option) {
                case 0: keyLength = 128;
                        break;
                case 1: keyLength = 192;
                        break;
                case 2: keyLength = 256;
                        break;
                default: return;
            }
            AESEncrypto enc = new AESEncrypto(keyLength);
            String encContent = enc.encryptAES(view.getEditorContent());
            if (saveFile(encContent)) {
                view.informUser("This is the " + keyLength + " bit long key for this document:\n" + enc.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAES() {
        String encContent = openFile();
        String key = view.askForInput("Please input the key for this file below:");
        try {
            AESDecrypto decriptor = new AESDecrypto(key);
            String decContent = decriptor.decryptAES(encContent);
            view.setEditorContent(decContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File selectFile(boolean forSave) {
        JFileChooser fileChooser = new JFileChooser();
        if (forSave) {
            fileChooser.showSaveDialog(view);
            if (fileChooser.getSelectedFile() != null) {
                return fileChooser.getSelectedFile();
            }
        } else {
            if (fileChooser.showOpenDialog(view) == JFileChooser.OPEN_DIALOG && fileChooser.getSelectedFile() != null) {
                return fileChooser.getSelectedFile();
            }
        }
        return null;
    }
}
