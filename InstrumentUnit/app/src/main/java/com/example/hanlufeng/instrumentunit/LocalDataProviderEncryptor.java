package com.example.hanlufeng.instrumentunit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by hanlu.feng on 1/18/2016.
 */
public class LocalDataProviderEncryptor extends Encryptor {


    private static LocalDataProviderEncryptor sInstance;
    private static final String INPUT_KEY_ENCODING = "UTF-8";
//    private static  int SALT = 1234567890;
    private static final Algorithm ALGORITHM = Algorithm.AES;
    private static final int KEY_SIZE = 16;
    private static String inputKey = "1234567890";
    private static byte[] IV = null;

    public static LocalDataProviderEncryptor getInstance() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (sInstance == null) {
                    inputKey = generateRandomKey(KEY_SIZE);
                    IV = generateIv();
                    sInstance = new LocalDataProviderEncryptor(inputKey.getBytes(INPUT_KEY_ENCODING));
        }
        return sInstance;
    }

    private LocalDataProviderEncryptor(byte[] inputKey) {
        super(inputKey,  new IvParameterSpec( IV), ALGORITHM);
    }

    /**
     * Change new Iv,and inputKey, Iv and inputkey are random generated
     * @throws UnsupportedEncodingException
     */
    public  void changeInputKeyAndIV() throws UnsupportedEncodingException {
        if(null != sInstance) {
            setInputKey(generateRandomKey(KEY_SIZE));
            setIV(getIV());
            sInstance.changeIVSaltAndInputKey(inputKey.getBytes(INPUT_KEY_ENCODING),  new IvParameterSpec(IV), ALGORITHM);
        }
    }


    /**
     * Change new Iv,and inputKey
     * @throws UnsupportedEncodingException
     */
    public  void changeInputKeyAndIV(String input_key, byte[] encrypt_IV) throws UnsupportedEncodingException {
        if(null != sInstance) {
            setInputKey(input_key);
            setIV(encrypt_IV);
            sInstance.changeIVSaltAndInputKey(inputKey.getBytes(INPUT_KEY_ENCODING),  new IvParameterSpec(IV), ALGORITHM);
        }
    }


    /**
     *
     * @param mEncryptResponseData
     * @return Decrypted ResponseData with Iv and sessionkey and change it to hex
     * @throws IOException
     * @throws InvalidKeyException
     */
    public DecryptResponseData getDecryResponseData(EncryptResponseData mEncryptResponseData) throws IOException, InvalidKeyException {
        byte[] frontIamgeBytes = ImageUtil.readFileToByteArray(mEncryptResponseData.getFrontImage());
        byte[] backImageBytes = ImageUtil.readFileToByteArray(mEncryptResponseData.getBackImage());
        byte []encryptedFrontImageBytes = getEncryptData(frontIamgeBytes);
        byte []encryptedBackImageBytes = getEncryptData(backImageBytes);

        DecryptResponseData mDecryptResponseData = new DecryptResponseData();
        mDecryptResponseData.setBackImage(Util.bytesToHex(encryptedBackImageBytes));
        mDecryptResponseData.setFrontImage(Util.bytesToHex(encryptedFrontImageBytes));
        mDecryptResponseData.setImageSessionKey(getInputKey());
        mDecryptResponseData.setImageIV(getIV());
        return mDecryptResponseData;

    }

    public DecryptResponseData getDecryResponseData2(EncryptResponseData mEncryptResponseData) throws IOException, InvalidKeyException {
        byte[] frontIamgeBytes = mEncryptResponseData.getFrontImage().getBytes("UTF-8");
        byte []encryptedFrontImageBytes = getEncryptData(frontIamgeBytes);

        DecryptResponseData mDecryptResponseData = new DecryptResponseData();
        mDecryptResponseData.setFrontImage(Util.bytesToHex(encryptedFrontImageBytes));
        mDecryptResponseData.setImageSessionKey(getInputKey());
        mDecryptResponseData.setImageIV(getIV());
        return mDecryptResponseData;

    }




    public String  getEncryBitmapData2(DecryptResponseData mDecryptResponseData) throws InvalidKeyException, UnsupportedEncodingException {
        byte [] encryFrontImageBytes =Util.hexStringToBytes(mDecryptResponseData.getFrontImage());
        changeInputKeyAndIV(mDecryptResponseData.getImageSessionKey(), mDecryptResponseData.getImageIV());
        byte[] frontImageBytes = getDecryptData(encryFrontImageBytes);
        return new String(frontImageBytes);
    }

    /**
     *Give the DecryptResponseData encrypted by Iv and sessionkey, it will return decrypted front Image and back Image
     * @param mDecryptResponseData
     * @return Two Bitmap, the first is the front Image, the second one is the back Image
     * @throws InvalidKeyException
     */
    public Bitmap[]  getEncryBitmapData(DecryptResponseData mDecryptResponseData) throws InvalidKeyException, UnsupportedEncodingException {
        byte [] encryFrontImageBytes =Util.hexStringToBytes(mDecryptResponseData.getFrontImage());
        byte [] encryBackImageBytes = Util.hexStringToBytes(mDecryptResponseData.getBackImage());
        changeInputKeyAndIV(mDecryptResponseData.getImageSessionKey(), mDecryptResponseData.getImageIV());
        byte[] frontImageBytes = getDecryptData(encryFrontImageBytes);
        byte[] backImageBytes = getDecryptData(encryBackImageBytes);
        Bitmap frontBitmap = BitmapFactory.decodeByteArray(encryFrontImageBytes, 0, frontImageBytes.length);
        Bitmap backBitmap = BitmapFactory.decodeByteArray(encryBackImageBytes,0,backImageBytes.length);
        return new Bitmap[] {frontBitmap,backBitmap};
    }

    /**
     * Encrypt data and return the result
     * @param data
     * @return
     * @throws InvalidKeyException
     */
    public byte[] getEncryptData(byte[] data) throws InvalidKeyException {
        byte[] encryptedData = encrypt(data);
        return Base64.encode(encryptedData, Base64.NO_WRAP);
    }

    /**
     * Decrypt the data and get the result
     * @param data
     * @return
     * @throws InvalidKeyException
     */
    public byte[] getDecryptData(byte[] data)throws InvalidKeyException {
        byte[] decodedEncryptedData = Base64.decode(data, Base64.NO_WRAP);
        return decrypt(decodedEncryptedData);
    }

    private static byte[] generateIv() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        SecretKey sk = kg.generateKey();
        return sk.getEncoded();
    }


    private static String generateRandomKey(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }



    public static String getInputKey() {
        return inputKey;
    }

    private static void setInputKey(String inputKey) {
       LocalDataProviderEncryptor.inputKey = inputKey;
    }

    public static byte[] getIV() {
        return IV;
    }

    private static void setIV(byte[] IV) {
       LocalDataProviderEncryptor.IV = IV;
    }
}
