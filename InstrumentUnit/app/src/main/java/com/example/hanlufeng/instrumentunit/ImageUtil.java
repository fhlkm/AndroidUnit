package com.example.hanlufeng.instrumentunit;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.Environment;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hanlu.feng on 1/19/2016.
 */
public class ImageUtil {
    private static final String FOLDER_NME="loyalty";
    private static String DIRECTORY =null;

    /**
     * Get external storage directory and creat an empty folder called "loyalty" under it
     * System.getProperty("line.separator")
     * @return path
     */
    private static String getDirectory(){
        if(null == DIRECTORY){
            DIRECTORY = Environment.getExternalStorageDirectory().getPath()+File.separator+FOLDER_NME;
            File file = new File(DIRECTORY);
            file.mkdir();
        }
        return DIRECTORY;
    }
    /**
     * @param file      The file will be written to
     * @param imageBytes date will be written
     * @return true if write success
     * @throws IllegalArgumentException file doesn't exist
     * @throws IOException              write file error
     */
    private static boolean saveEncryptedImage(File file, byte[] imageBytes) throws IllegalArgumentException, IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imageBytes);
        fos.close();
        return true;
    }

    /**
     * Create a file and save imageBytes
     * @param fileName
     * @param imageBytes
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static File saveEncryptedImage(String fileName, byte[] imageBytes)throws IllegalArgumentException, IOException {
        File mFile = new File(getDirectory()+File.separator+fileName);
        if (!mFile.exists()) {
            mFile.createNewFile();
        }else{
            saveEncryptedImage(mFile,imageBytes);
        }
        return mFile;
    }

    /**
     * Read bytes from a file
     * @param fileName
     * @return
     * @throws IOException
     */
    public static byte[] readFileToByteArray(final String fileName) throws IllegalArgumentException,IOException{
        File mFile = new File(getDirectory()+System.getProperty("line.separator")+fileName);
        if (mFile == null || !mFile.exists()) {
            throw new IllegalArgumentException(fileName + " does not exist");
        }else{
            return readFileToByteArray(mFile);
        }
    }

    /**
     *
     * @param file The file will read data from
     * @return bytes in file
     * @throws IOException read file error
     */
    private static byte[] readFileToByteArray(final File file) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return toByteArray(in, file.length());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * @param input InputStream that will be changed to byte
     * @param size  the size of the file that is changed to Input Stream
     * @return byte array of InputStream
     * @throws IOException If the size of the file is bigger than Integer.MAX_VALUE
     */
    private static byte[] toByteArray(final InputStream input, final long size) throws IOException {

        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        }

        return toByteArray(input, (int) size);
    }

    private static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }


    /**
     * @param input InputStream that will be changed to byte
     * @param size  the size of the file that is changed to Input Stream
     * @return byte array of InputStream
     * @throws IOException If there is reading error in InputStream
     */
    private static byte[] toByteArray(final InputStream input, final int size) throws IOException {

        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }

        if (size == 0) {
            return new byte[0];
        }

        final byte[] data = new byte[size];
        int offset = 0;
        int readed;

        while (offset < size && (readed = input.read(data, offset, size - offset)) != -1) {
            offset += readed;
        }

        if (offset != size) {
            throw new IOException("Unexpected readed size. current: " + offset + ", excepted: " + size);
        }

        return data;
    }

}
