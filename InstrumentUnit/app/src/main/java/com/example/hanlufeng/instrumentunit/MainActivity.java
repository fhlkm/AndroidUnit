package com.example.hanlufeng.instrumentunit;

import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button button = (Button) findViewById(R.id.bun);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
//                    InputStream in  = getAssets().open("forest.jpg");
//                    int lenght = in.available();
//                    byte[]  buffer = new byte[lenght];
//
//                    in.read(buffer);
//                    ImageUtil.saveEncryptedImage("hello.txt",buffer);
                    ImageUtil.readFileToByteArray("nihao");
                }catch (IllegalArgumentException e){
                    Log.i("MainActivity","this is an error no this file");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

//                read4();
            }


        });
//        read2();
//        try {
//            read3();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    public void read3() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher =    Cipher.getInstance("AES/GCM/NoPadding");
        String keys="1234567890abcDEF";
        String ivs = "1234567890abcDEF";
        String y="Hello";
        IvParameterSpec mIvParamSpec = new IvParameterSpec(ivs.getBytes());
        SecretKeySpec  mSecretKey = new SecretKeySpec(keys.getBytes(), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, mSecretKey, mIvParamSpec);
        String temp ="";
        for(byte b : y.getBytes()){
            temp = temp+b+" ";
        }
        Log.i("Buffer3:  ",temp);


        byte[] result = cipher.doFinal(y.getBytes());
        temp ="";
        for(byte b : result){
            temp = temp+b+" ";
        }

        Log.i("Buffer3:  ",temp);



    }
    public void read2() {


        try {
            InputStream in  = getAssets().open("forest.jpg");
            int lenght = in.available();
            byte[]  buffer = new byte[lenght];

            in.read(buffer);
//
            String hello = new String("Hello China");
            buffer = hello.getBytes("UTF-8");
            String temp ="";
            for(int i =0; i<buffer.length;i++){
                temp = temp+buffer[i]+" ";
            }
            Log.i("Buffer1:  ", temp);
            LocalDataProviderEncryptor ldEn = LocalDataProviderEncryptor.getInstance();
//
//
//
            byte [] encrpted = ldEn.getEncryptData(buffer);
           String hex =  Util.bytesToHex(encrpted);
            Log.i("BufferString :  ",hex);
//
            temp ="";
            for(int i =0; i<encrpted.length;i++){
                temp = temp+encrpted[i]+" ";
            }
            Log.i("Buffer2:  ",temp);

            byte [] decrpted = ldEn.getDecryptData(encrpted);
            boolean result =  Arrays.equals(buffer, decrpted);

            Log.i("Result :  ",result+"");






            Assert.assertEquals(result, true);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InvalidKeyException ke){
            ke.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    public void read4(){




        try {

            LocalDataProviderEncryptor ldEn = LocalDataProviderEncryptor.getInstance();
//
            EncryptResponseData mEncryData = new EncryptResponseData();
            mEncryData.setFrontImage("hello2");

            DecryptResponseData mDedata=  ldEn.getDecryResponseData2(mEncryData);
            String result = ldEn.getEncryBitmapData2(mDedata);
            Log.i("stringinfo",result);






//            Assert.assertEquals(result, true);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InvalidKeyException ke){
            ke.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

}
