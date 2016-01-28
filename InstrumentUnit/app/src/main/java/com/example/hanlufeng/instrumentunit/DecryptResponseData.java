package com.example.hanlufeng.instrumentunit;

/**
 *
 * Created by hanlu.feng on 1/25/2016.
 */
public class DecryptResponseData {
    /**
     * Encrypted frontImage hex string  by {@link #getImageIV }and {@link #getImageSessionKey }
     */
    String frontImage;
    /**
     * Encrypted frontImage hex string by {@link #getImageIV }and {@link #getImageSessionKey }
     */
    String backImage;

//    byte[] frontImage;
//
//
//    byte[] backImage;

    private String imageSessionKey;
    private byte[] imageIV;

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    //
//    public byte[] getFrontImage() {
//        return frontImage;
//    }
//
//    public void setFrontImage(byte[] frontImage) {
//        this.frontImage = frontImage;
//    }
//
//    public byte[] getBackImage() {
//        return backImage;
//    }
//
//    public void setBackImage(byte[] backImage) {
//        this.backImage = backImage;
//    }

    public String getImageSessionKey() {
        return imageSessionKey;
    }

    public void setImageSessionKey(String imageSessionKey) {
        this.imageSessionKey = imageSessionKey;
    }

    public byte[] getImageIV() {
        return imageIV;
    }

    public void setImageIV(byte[] imageIV) {
        this.imageIV = imageIV;
    }
}
