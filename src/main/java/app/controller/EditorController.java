package app.controller;

import app.data.FileManager;
import app.model.*;
import app.model.cryptofactories.DecryptorFactory;
import app.model.cryptofactories.EncryptorFactory;
import app.model.cryptomodes.rsa.RSAKeyManager;
import app.view.EditorView;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is the controller of the editor.
 * It delegate user actions to the write methods and functions.
 * It informs user and controls the view according to the binded model layer.
 * Also it prepares the configuration for the model, by asking the user for some input.
 *
 * @author Anfrej Tihonov
 * @version 1.0
 */
public final class EditorController {
    /**
     * The view of the texteditor.
     */
    private final EditorView view;

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
     * This method opens a file and reads it content out.
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
        String[] paddingOptions = new String[] {"NoPadding", "PKCS7Padding", "ZeroBytePadding"};
        try {
            return view.createOptionDialog(paddingOptions, "Padding selection", "Select the padding mechanism!");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method lets the user to choose between three key sizes for the AES Encryption.
     * @return the size of the aes Key.
     */
    private int chooseKeysize() {
        String[] options = new String[] {"128 Bit", "192 Bit", "256 Bit"};
        String option = view.createOptionDialog(options, "Keysize selection", "Select the aes keysize!");
        return Integer.parseInt(option.substring(0, 3));
    }


    /**
     * This method lets the user to choose between several block modi for the encryption.
     * @return the selected block modi.
     */
    private String chooseBlockModus(String type) {
        if (type.equals("AES")) {
            return view.createOptionDialog(new String[] {"ECB", "CBC", "CTS", "OFB", "GCM"}, "Block modus selection", "Select the block modus");
        } else {
            return view.createOptionDialog(new String[] {"ECB", "CBC", "CTS", "OFB"}, "Block modus selection", "Select the block modus");
        }
    }

    /**
     * This method lets the user to choose between several hash types for the encryption.
     * @return the selected hashtype.
     */
    private String chooseHashType() {
        return view.createOptionDialog(new String[] {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"}, "Hash modus selection", "Select the Hash modus");
    }

    /**
     * This method lets the user to choose between several password based encryption modes.
     * @return the selected password based encryption mode.
     */
    private String choosePBE() {
        return view.createOptionDialog(new String[] {"PBEWithSHAAnd128BitAES-CBC-BC", "PBEWithMD5AndDES", "PBEWithSHAAnd40BitRC4"}, "PBE modus selection", "Select the PBE modus");
    }

    /**
     * This method lets the user to choose between two keysizes for the RSA encrypion.
     * @return the selected keysize for the rsa encryption (1024 or 2048).
     */
    private String chooseRSAKeysize() {
        return view.createOptionDialog(new String[] {"1024", "2048"}, "RSA keysize selection", "Select the keysize");
    }

    /**
     * This method prepares the configuration files for the AES encryption and
     * is called when the user want to save a file in the aes mode. It delegates the configuration file
     * for other preparations.
     */
    public void saveAES() {
        Configuration config = new Configuration();
        config.addSetting("Type", "AES");
        config.addSetting("Keysize", String.valueOf(chooseKeysize()));
        prepareConfiguration(config);
    }

    /**
     * This method prepares the configuration files for the DES encryption and
     * is called when the user want to save a file in the aes mode. It delegates the configuration file
     * for other preparations.
     */
    public void saveDES() {
        Configuration config = new Configuration();
        config.addSetting("Type", "DES");
        prepareConfiguration(config);
    }

    /**
     * This method prepares the configuration files for the RSA encryption and
     * is called when the user want to save a file in the aes mode. It delegates the configuration file
     * for other preparations.
     */
    public void saveRSA() {
        try {
            view.informUser("Select public Key!");
            //Choosing the public key for the encryption.
            Configuration config = new Configuration(openFile());
            config.addSetting("Type", "RSA");
            performSave(config);
        } catch (Exception e) {
            view.informUser("Error: " + e.getMessage());
        }
    }

    /**
     * This method prepares the configuration files for the Password based encryption and
     * is called when the user want to save a file in the aes mode. It delegates the configuration file
     * for other preparations.
     */
    public void savePBE() {
        Configuration config = new Configuration();
        config.addSetting("PbeModus", choosePBE());
        performSave(config);
    }

    /**
     * This method performs the encryption and saves finally the encrypted content.
     * It get the configuration file and depending on the configuration, selects
     * the right Encryptor through the factory, which encrypts the content.
     *
     * @param config, the configuration for the encryptor, which was prepared through interactions
     *                with the user.
     */
    private void performSave(Configuration config) {
        try {
            //Choosing the hashtype.
            config.addSetting("HashType", chooseHashType());
            Encryptor encryptor;
            //If there should be used PBE, there is a need to ask for password.
            //The Encryptor factory creates also the key, so there is an update of the config, for storing the salt,
            //which is used by the PBE
            if (config.settingExist("PbeModus")) {
                EncryptorFactory encryptorFactory = new EncryptorFactory();
                //Asking for user to input a pass.
                String pass = view.askForPassword(true);
                encryptor = EncryptorFactory.getEncryptor(config, pass);
                //Updating configuration for storing salt
                config = encryptorFactory.getConfig();
            //The case, when there is no PBE was selected.
            } else {
                encryptor = EncryptorFactory.getEncryptor(config);
            }
            //Encrypting the content as with base64 to string.
            String encContent = encryptor.encryptAsString(view.getEditorContent());
            //Updating the configuration file, mostly for storing the key, the iv. But also sometimes
            //to remove unusefull information, like public key.
            config = encryptor.updateConfiguration(config);
            //Saving the encrypted file.
            if (saveFile(encContent)) {
                view.informUser("Save the configuration file!");
                //Saving the configuration file
                if (saveFile(config.toString())) {
                    view.informUser("Your file was saved and the configuration file was created!");
                }
            }
        } catch (Exception e) {
            view.informUser("Error:" + e.getMessage());
        }
    }

    /**
     * This method preapres configuration for block modes, by selecting the block mode.
     * @param config, the configuration, which will be used for encryption.
     */
    private void prepareConfiguration(Configuration config) {
        String blockModus;
        //AES supports more modes, than DES, so some modes should be unavailible
        if (config.getSetting("Type").equals("AES")) {
            blockModus = chooseBlockModus("AES");
        } else {
            blockModus = chooseBlockModus("DES");
        }
        config.addSetting("BlockModus", blockModus);
        try {
            //Selection the padding.
            config = preparePadding(config);
        } catch (IllegalStateException e) {
            view.informUser(e.getMessage());
            return;
        }
        performSave(config);
    }

    /**
     * This method updates the configuration, by adding the padding.
     * @param config, the configuration, which should be updated by adding the padding.
     * @return the updated configuration with padding.
     */
    private Configuration preparePadding(Configuration config) {
        String blockModus = config.getSetting("BlockModus");
        //OFB and GCM blockmodi do not need padding
        if (!blockModus.equals("OFB") && !blockModus.equals("GCM")) {
            String padding = choosePadding();

            /*  AES blocksize is 16, DES has a blocksize of 8, so if thge user want to choose blockcipher mode
            *   and NoPadding the size of text must be the multiple of 16 or of 8, debending on the crypto type
            */
            int blockSize;
            if (config.getSetting("Type").equals("AES")) {
                blockSize = 16;
            } else {
                assert !blockModus.equals("GCM");
                blockSize = 8;
            }
            /*
             * In this case the block mode would not work
             */
            if (padding.equals("NoPadding") && view.getEditorContent().length() % blockSize != 0) {
                throw new IllegalStateException("You can not use NoPadding, with " + blockModus + " and this text length!");
            }
            config.addSetting("Padding", padding);
        } else {
            config.addSetting("Padding", "NoPadding");
        }
        return config;
    }

    /**
     * This method opens the encrypted file, lets the user choose the configuration file
     * and may be input a password if required and finally decrypting the encrypted file.
     * @return the decrypted content of the encrypted file.
     */
    public String openEncryptedFile() {
        try {
            //Reading the content out of file.
            String encContent = openFile();
            //Asking for the configuration file.
            view.informUser("Select the configuration file for this encrypted text!");
            Configuration configuration = new Configuration(openFile());
            Decryptor decryptor;
            //Asking for the private key, if the file was encrypted with RSA
            if (!configuration.settingExist("PbeModus") && configuration.getSetting("Type").equals("RSA")) {
                view.informUser("Select the private key!");
                Configuration privKey = new Configuration(openFile());
                //updating the configuration by adding the private key to it.
                configuration.addSetting("PrivateKey", privKey.getSetting("PrivateKey"));
            }
            //Asking for pass, if the file was encrypted with PBE and passing the pass to the factory.
            if (configuration.settingExist("PbeModus")) {
                String pass = view.askForPassword(false);
                decryptor = DecryptorFactory.getDecryptor(configuration, pass);
            //The case, when there was PBE or RSA were not used.
            } else {
                decryptor = DecryptorFactory.getDecryptor(configuration);
            }
            //Decrypting the content.
            String decContent = decryptor.decryptAsString(encContent);
            //Checking with the help of hash mechanisms if the file was updated and informing the user about it.
            if (!decryptor.getIsValidMessage()) {
                view.informUser("Warning, the text was modified!");
            }
            return decContent;
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                return "";
            }
            e.printStackTrace();
            //Informing the user in case of some errors.
            view.informUser("Error! Not able to decrypt the file, maybe it was modified or the given password was incorrect!");
        }
        return "";
    }

    /**
     * This method generates RSA-Key pair and saving it in two separate files, in the location
     * choosed by the user.
     */
    public void generateRSAKeys() {
        try {
            //Generating the keys.
            Configuration keyPair = RSAKeyManager.generateKeyPair(Integer.parseInt(chooseRSAKeysize()));
            //Saving the public key.
            view.informUser("Save public key");
            Configuration publicK = new Configuration();
            publicK.addSetting("PublicKey", keyPair.getSetting("PublicKey"));
            saveFile(publicK.toString());
            //Saving the private key.
            view.informUser("Save private key");
            Configuration privateK = new Configuration();
            privateK.addSetting("PrivateKey", keyPair.getSetting("PrivateKey"));
            saveFile(privateK.toString());
        } catch (Exception e) {
            view.informUser("Error: " + e.getMessage());
        }
    }
}
