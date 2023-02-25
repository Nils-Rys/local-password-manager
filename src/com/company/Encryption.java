package com.company;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
    private byte[] key;
    private final byte ivByte = (byte) 654321;
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
            byte[] messageDigest;
            try {
                messageDigest = message.digest(input.getBytes(StandardCharsets.UTF_8));
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }


            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(number.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
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
        byte[] nameBytes;
        byte[] passwordBytes;
        try {
            nameBytes = name.getBytes(StandardCharsets.UTF_8);
            passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
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
     * @return data with length of length
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

    /**
     * encrypts data using CBC encryption and the key that was corrected
     * @param data string we wish to encrypt
     * @return encrypted string
     */
    public String encrypt(String data) {
        if(key == null) {
            System.err.println("Error: key was never set");
            System.exit(1);
        }

        byte[] padMessage = padData(data);

        assert padMessage != null;
        byte[] rvByte = new byte[padMessage.length];

        int times = padMessage.length / keyLength;
        byte[] lastValue = new byte[keyLength];
        lastValue[0] = ivByte;

        for (int i = 0; i < times; i++) {
            byte[] input = xor(lastValue, getPart(padMessage, i*keyLength, (i+1)*keyLength));

            lastValue = blockCipherEncrypt(input);
            assert lastValue != null;
            System.arraycopy(lastValue, 0, rvByte, (i * keyLength), keyLength);
        }
        try {
            String rv = new String(rvByte, StandardCharsets.UTF_8);
            return rv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String data) {
        if(key == null) {
            System.err.println("Error: key was never set");
            System.exit(1);
        }

        String rv = "";
        byte[] dataByte;
        try {
            dataByte = data.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(dataByte);
        int times = data.length() / keyLength;
        byte[] lastValue = new byte[keyLength];
        lastValue[0] = ivByte;

        for (int i = 0; i < times; i++) {
            byte[] block = blockCipherDecrypt(getPart(dataByte, i*keyLength, (i+1)*keyLength));
            rv += xor(lastValue, block);
            lastValue = getPart(dataByte, i*keyLength, (i+1)*keyLength);
        }

        return rv;
    }

    /**
     * xor twos arrays, arrays must be the same length
     * @param arr1
     * @param arr2
     * @return xor of two arrays
     */
    private byte[] xor(byte[] arr1, byte[] arr2)  {
        if(arr1.length != arr2.length) {
            System.err.println("Error: arr1 length (" + arr1.length + ") and arr2 length (" + arr2.length + ") are not the same!");
            return null;
        }
        int length = arr1.length;

        byte[] rv = new byte[length];

        for (int i = 0; i < length; i++) {
            rv[i] = (byte) (arr1[i] ^ arr2[i]);
        }

        return rv;
    }

    /**
     *
     * @param arr array to parse
     * @param startPos position of where to start coping from includes this value
     * @param endPos position of where to stop coping from, excludes this value
     * @return arr from position startPos to position endPos
     */
    private byte[] getPart(byte[] arr, int startPos, int endPos) {
        int length = endPos - startPos;
        byte[] rv = new byte[length];
        int pos = 0;

        for (int i = startPos; i < endPos; i++) {
            rv[pos] = arr[i];
            pos++;
        }

        return rv;
    }

    /**
     *
     * @param data data we want to pad
     * @return data such that data.length % keyLength = 0
     */
    private byte[] padData(String data) {
        int dataLength = data.length();

        int padAmount = keyLength - (dataLength % keyLength);
        StringBuilder rv = new StringBuilder(data);

        rv.append(String.valueOf((byte) padAmount).repeat(Math.max(0, padAmount)));

        byte[] rvByte;
        try {
            rvByte = rv.toString().getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            String retrievedString = new String(rvByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rvByte;
    }

    /**
     *
     * @param data
     * @return
     */
    private byte[] unpadData(byte[] data) {
        int dataLen = data.length;
        int padAmount = data[dataLen - 1];
        int rvLen = dataLen - padAmount;
        byte[] rv = new byte[rvLen];

        for (int i = 0; i < rvLen; i++) {
            rv[i] = data[i];
        }

        return rv;
    }

    private byte[] blockCipherEncrypt(byte[] message) {
        try {
            Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
            SecretKeySpec newKey = new SecretKeySpec(key, "AES");
            aes.init(Cipher.ENCRYPT_MODE, newKey);

            return aes.doFinal(message);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] blockCipherDecrypt(byte[] message) {
        try {
            Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
            SecretKeySpec newKey = new SecretKeySpec(key, "AES");
            aes.init(Cipher.DECRYPT_MODE, newKey);

            return aes.doFinal(message);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
