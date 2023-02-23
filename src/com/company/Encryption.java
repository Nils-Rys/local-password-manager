package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private byte[] key;

    private static final Encryption onlyInstance = new Encryption();

    public static Encryption singleton() {
        return onlyInstance;
    }

    public Encryption() {

    }

/*
    Sha-512 hashes given string inputs.
 */
    public String encryptThisString(String input) {
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
    public byte[] initializeKey(String name, String password) {
        byte[] nameBytes = name.getBytes();
        byte[] passwordBytes = password.getBytes();
        return createKey(nameBytes, passwordBytes);
    }

    /***
     * Creates master key to be used for encryption and decryption
     * @param name users name in bytes
     * @param password users master password in bytes
     */
    private byte[] createKey(byte[] name, byte[] password) {
        int keyLength = 16;
        byte[] keyBytes = new byte[keyLength];

        byte[] nameBytes = makeKeyLength(name, keyLength);
        byte[] passwordBytes = makeKeyLength(password, keyLength);

        for (int i = 0; i < keyLength; i++) {
            keyBytes[i] = (byte) (nameBytes[i] ^ passwordBytes[i]);
        }

        key = keyBytes;
        return key;
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
}
