package app.model.cryptomodes.rsa;


import app.model.Decryptor;

import javax.crypto.Cipher;

/**
 * This class creates an RSA decryptor.
 *
 * @author Andrej Tihonov
 * @version 1.0
 *
 */
public final class RSADecryptor extends Decryptor {

    /**
     * This method creates an instance of the RSA decryptor.
     * @param hash, the hash this decryptor is using.
     * @param key, the private key for the decryption.
     * @throws Exception, is thrown when something bad happens
     */
    public RSADecryptor(String hash, String key) throws Exception {
        super(hash);
        setKey(RSAKeyManager.privateKeyFromString(key));
    }

    @Override
    protected byte[] decrypt(byte[] input) throws Exception {
        //Getting the cipher from the bouncy castle provider.
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        //setting decryption mode and settuing the private key
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        //creating the array for the plaintext
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        //decrypting the content
        int ctLength = cipher.update(input, 0, input.length, output, 0);
        //decrypting the last bytes
        ctLength += cipher.doFinal(output, ctLength);
        return removeHash(output, ctLength);
    }
}
