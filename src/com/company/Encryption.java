package com.company;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private byte[] key;
    int keyLength = 16;

    private static final Encryption onlyInstance = new Encryption();

    public static Encryption singleton() {
        return onlyInstance;
    }

    public Encryption() {

    }

/*
    Sha-512 hashes given string inputs.
 */
    public String hashString(String input) {
        try {
            MessageDigest message = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = message.digest(input.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        private key

        Public void passInfo(user.name, user.password)
            makeMasterKey(user.name, user.password)

        private void makeMasterKey(user.name, user.password)
            key = user.name ^ user.password

        public string encryption(data)
            use CBC

        public string hash(data)
            use SHA-512

        public string decryption
            user cbc
     */

    /***
     * Creates master password used for decryption and encryption
     * @param name as a string
     * @param password as a string
     */
    public void initializeKey(String name, String password) {
        byte[] nameBytes = name.getBytes();
        byte[] passwordBytes = password.getBytes();
        createKey(nameBytes, passwordBytes);
    }

    /***
     * Creates master key to be used for encryption and decryption
     * @param name users name in bytes
     * @param password users master password in bytes
     */
    private void createKey(byte[] name, byte[] password) {
        byte[] keyBytes = new byte[keyLength];

        byte[] nameBytes = makeKeyLength(name, keyLength);
        byte[] passwordBytes = makeKeyLength(password, keyLength);

        for (int i = 0; i < keyLength; i++) {
            keyBytes[i] = (byte) (nameBytes[i] ^ passwordBytes[i]);
        }

        key = keyBytes;
    }

    /***
     * changes data length so that it is the length
     * @param data the data we want to pad or shrink
     * @param length how long we
     * @return data with length of lenght
     */
    private byte[] makeKeyLength(byte[] data, int length) {
        int dataLength = data.length;
        byte[] rv = new byte[length];

        if(dataLength >= length) {
            System.arraycopy(data, 0, rv, 0, length);
        }
        else {
            int toPad = dataLength % length;
            byte pad = (byte)toPad;
            System.arraycopy(data, 0, rv, 0, dataLength);

            for (int i = 0; i < toPad; i++) {
                rv[i] = pad;
            }
        }
        return rv;
    }

    public String encrypt(String data)
    {
        String rv = "";

        return rv;
    }

    /*
    def padding(data: str, keyLen: int):
        """
        Pads data such that padded values are bytes of how many padded bytes there are
        pad 1: data 1
        pad 2: data 2 2
        ...
        Args:
            data: string, plaintext to pad
            length: length of key

        Returns:
            padded: byte padded string
        """
        rv = bytes(data, 'utf-8')
        padamount = keyLen - (len(data) % keyLen)

        if padamount != keyLen:
            rv = rv + padamount.to_bytes(1, 'big') * padamount

        return rv
    */

    private byte[] padData(byte[] data) {
        byte[] rv = new byte[keyLength];

        return rv;
    }

    /*
    def unpadding(text: str, keyLen: int):
        """
        Depads text, assume that last byte is how many chars needs to be removed
        Args:
            text: string, plaintext with padding
            keyLen: lenght of key
        Returns:
            text: text wihtout padding
        """
        paddedAmount = int.from_bytes(text[-1:], 'big')
        if paddedAmount > 0 and paddedAmount < keyLen:
            return text[:len(text)-paddedAmount]
        else:
        return text
     */

    private byte[] blockCipherEncrypt(byte[] message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec newKey = new SecretKeySpec(key, "AES");
        aes.init(Cipher.ENCRYPT_MODE, newKey);

        return aes.doFinal(message);
    }

    private byte[] blockCipherDecrypt(byte[] message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec newKey = new SecretKeySpec(key, "AES");
        aes.init(Cipher.DECRYPT_MODE, newKey);

        return aes.doFinal(message);
    }
}
