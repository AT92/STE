package app.controller;

import app.data.ContentManager;
import app.controller.view.EditorView;
import app.logic.AES.AESDecrypto;
import app.logic.AES.AESEncrypto;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;


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
            ContentManager contentReader = new ContentManager(selectFile(false));
            return contentReader.getContentOutOfFile();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei wurde nicht gefunden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gelesen werden!");
        } catch (NullPointerException n) {

        }
        return "";
    }

    public void saveFile(String content) {
        try {
            ContentManager contentWriter = new ContentManager(selectFile(true));
            contentWriter.writeContentToFile(content);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Die Datei konnte nicht gespeichert werden!");
        } catch (IOException i) {
            JOptionPane.showMessageDialog(view, "Es ist ein Fehler bei dem Schreiben der Datei passiert!");
        } catch (NullPointerException n) {

        }
    }

    public void saveAES() {
        try {
            AESEncrypto enc = new AESEncrypto(128);
            String encContent = enc.encryptAES(view.getEditorContent());
            saveFile(encContent);
            JOptionPane.showMessageDialog(view, enc.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAES(String key) {
        AESDecrypto dec = new AESDecrypto(key);
        try {
            String content = dec.decryptAES(openFile());
            view.setEditorContent(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File selectFile(boolean forSave) {
        JFileChooser fileChooser = new JFileChooser();
        if (forSave) {
            fileChooser.showSaveDialog(view.saveButton);
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
