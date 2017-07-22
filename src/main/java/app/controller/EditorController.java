package app.controller;

import app.data.FileManager;
import app.model.*;
import app.model.cryptofactories.DecryptorFactory;
import app.model.cryptofactories.EncryptorFactory;
import app.view.EditorView;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is the controller of the editor.
 * It delegate user actions to the write methods and functions.
 * It informs user and controls the view according to the binded model layer.
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
     * This method lets the user to choose between three key sizes for the aes Encryption.
     * @return the size of the aes Key.
     */
    private int chooseKeysize() {
        String[] options = new String[] {"128 Bit", "192 Bit", "256 Bit"};
        String option = view.createOptionDialog(options, "Keysize selection", "Select the aes keysize!");
        return Integer.parseInt(option.substring(0, 3));
    }

    /**
     * This method lets the user to choose between three block modi for the encryption.
     * @return the selected block modi.
     */
    private String chooseBlockModus(String type) {
        if (type.equals("AES")) {
            return view.createOptionDialog(new String[] {"ECB", "CBC", "CTS", "OFB", "GCM"}, "Block modus selection", "Select the block modus");
        } else {
            return view.createOptionDialog(new String[] {"ECB", "CBC", "CTS", "OFB"}, "Block modus selection", "Select the block modus");
        }
    }

    public void saveAES() {
        Configuration config = new Configuration();
        config.addSetting("Type", "AES");
        prepareConfiguration(config);
    }

    public void saveDES() {
        Configuration config = new Configuration();
        config.addSetting("Type", "DES");
        prepareConfiguration(config);
    }

    private void performSave(Configuration config) {
        try {
            Encryptor encryptor = EncryptorFactory.getEncryptor(config);
            String encContent = encryptor.encryptAsString(view.getEditorContent());
            config = encryptor.updateConfiguration(config);
            if (saveFile(encContent)) {
                view.informUser("Save the configuration file!");
                if (saveFile(config.toString())) {
                    view.informUser("Your file was saved and the configuration file was created!");
                }
            }
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
    }

    private void prepareConfiguration(Configuration config) {
        String blockModus;
        if (config.getSetting("Type").equals("AES")) {
            config.addSetting("Keysize", String.valueOf(chooseKeysize()));
            blockModus = chooseBlockModus("AES");
        } else {
            blockModus = chooseBlockModus("DES");
        }
        config.addSetting("BlockModus", blockModus);
        if (!blockModus.equals("OFB") && !blockModus.equals("GCM")) {
            String padding = choosePadding();
            int blockSize = 0;
            if (config.getSetting("Type").equals("AES")) {
                blockSize = 16;
            } else {
                assert !blockModus.equals("GCM");
                blockSize = 8;
            }
            if (padding.equals("NoPadding") && view.getEditorContent().length() % blockSize != 0) {
                view.informUser("You can not use NoPadding, with " + blockModus + " and this text length!");
                return;
            }
            config.addSetting("Padding", padding);
        } else {
            config.addSetting("Padding", "NoPadding");
        }
        performSave(config);
    }


    public String openEncryptedFile() {
        String encContent = openFile();
        try {
            view.informUser("Select the configuration file for this decrypted text!");
            Configuration configuration = new Configuration(openFile());
            Decryptor decryptor = DecryptorFactory.getDecryptor(configuration);
            return decryptor.decryptAsString(encContent);
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
        return "";
    }
}
