package com.example.hanlufeng.instrumentunit;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by hanlu.feng on 1/19/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    MainActivity mainActivity;


    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);

    }
    public MainActivityTest() {
        super(MainActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();




    }

//    public void testRead() throws NoSuchAlgorithmException {
//        mainActivity = getActivity();
//        InputStream in = null;
//        try {
//            in = mainActivity.getAssets().open("forest.jpg");
//            int lenght = in.available();
//            byte[]  buffer = new byte[lenght];
//
//            in.read(buffer);
//
//            String cKey = "1234567890abcDEF";
//            LocalDataProviderEncryptor ldEn = LocalDataProviderEncryptor.getInstance();
//            ldEn.changeSaltAndInputKeyWithIV();
//
//            byte [] encrpted = ldEn.getDecryptData(buffer);
//            byte [] decrpted = ldEn.getDecryptData(encrpted);
//            boolean result =  Arrays.equals(buffer,decrpted);
//
//            Assert.assertEquals(result, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (InvalidKeyException ke){
//            ke.printStackTrace();
//        }
//
//
//    }


    public void testRead2() throws NoSuchAlgorithmException {
        mainActivity = getActivity();
        InputStream in = null;
        try {
            in = mainActivity.getAssets().open("forest.jpg");
            int lenght = in.available();
            byte[]  buffer = new byte[lenght];

            in.read(buffer);

            String hello = new String("Hello");
            buffer = hello.getBytes("UTF-8");
            String temp ="";
            for(int i =0; i<buffer.length;i++){
                temp = temp+buffer[i]+" ";
            }
            Log.i("Buffer1:  ",temp);
            LocalDataProviderEncryptor ldEn = LocalDataProviderEncryptor.getInstance();

            ldEn.changeSaltAndInputKeyWithIV();

            byte [] encrpted = ldEn.getEncryptData(buffer);

            for(int i =0; i<encrpted.length;i++){
                temp = temp+encrpted[i]+" ";
            }
            Log.i("Buffer2:  ",temp);

            byte [] decrpted = ldEn.getDecryptData(encrpted);
            boolean result =  Arrays.equals(buffer, decrpted);

            Assert.assertEquals(result, true);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InvalidKeyException ke){
            ke.printStackTrace();
        }


    }
}
