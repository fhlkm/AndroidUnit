package com.example.hanlufeng.instrumentunit;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hanlu.feng on 1/18/2016.
 */
public class Encryptor {
    private enum Operation {
        ENCRYPT, DECRYPT;
    }

    public enum Algorithm {
        DES, AES;
    }

    private SecretKey mSecretKey;
    private IvParameterSpec mIvParamSpec;
    private Algorithm mAlgorithm;

    public Encryptor(){

    }

    public Encryptor(byte[] generatedKey, IvParameterSpec ivSpec,
                     Algorithm algorithm) {
        switch (algorithm) {
            case DES:
                mSecretKey = new SecretKeySpec(generatedKey, "DES");
                break;
            case AES:
                mSecretKey = new SecretKeySpec(generatedKey, "AES");
                break;
        }
        mIvParamSpec = ivSpec;
        mAlgorithm = algorithm;
    }


    protected void changeIVSaltAndInputKey(byte[] inputKey, IvParameterSpec ivSpec,
                                        Algorithm algorithm){
        switch (algorithm) {
            case DES:
                mSecretKey = new SecretKeySpec(inputKey, "DES");
                break;
            case AES:
                mSecretKey = new SecretKeySpec(inputKey, "AES");
                break;
        }
        mIvParamSpec = ivSpec;
        mAlgorithm = algorithm;
    }



    protected byte[] encrypt(byte[] data) throws IllegalArgumentException,
            InvalidKeyException {
        return encryptOrDecrypt(data, Operation.ENCRYPT);
    }

    protected byte[] decrypt(byte[] data) throws IllegalArgumentException,
            InvalidKeyException {
        try {
            return encryptOrDecrypt(data, Operation.DECRYPT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Performs symmetric encryption or decryption of data.
     *
     * @return The encrypted or decrypted byte array data. Null on error.
     * @throws InvalidKeyException If mSymmetricKey is invalid.
     */
    private byte[] encryptOrDecrypt(byte[] data, Operation encryptOrDecrypt)
            throws InvalidKeyException {
        Cipher cipher = null;
        switch (mAlgorithm) {
            case DES:
                try {
                    cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                } catch (NoSuchAlgorithmException e) {
//                    LogHelper.e(e);
                } catch (NoSuchPaddingException e) {
//                    LogHelper.e(e);
                }

                break;
            case AES:
                try {
                    cipher = Cipher.getInstance("AES/GCM/NoPadding");
                } catch (NoSuchAlgorithmException e) {
//                    LogHelper.e(e);
                } catch (NoSuchPaddingException e) {
//                    LogHelper.e(e);
                }

                break;
        }

        byte[] modifiedData = null;
        try {
            switch (encryptOrDecrypt) {
                case ENCRYPT:
                    cipher.init(Cipher.ENCRYPT_MODE, mSecretKey, mIvParamSpec);
                    break;
                case DECRYPT:
                    cipher.init(Cipher.DECRYPT_MODE, mSecretKey, mIvParamSpec);
                    break;
            }

            modifiedData = cipher.doFinal(data);
        } catch (InvalidAlgorithmParameterException e) {
//            LogHelper.e(e);
        } catch (IllegalBlockSizeException e) {
//            LogHelper.e(e);
        } catch (BadPaddingException e) {
//            LogHelper.e(e);
        }

        return modifiedData;
    }
}
