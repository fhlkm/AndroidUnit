package com.example.hanlufeng.instrumentunit;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.Arrays;

/**
 * Created by hanlu.feng on 1/19/2016.
 */
public class detailActivityTest2 extends ActivityInstrumentationTestCase2<DetailActivity> {
    DetailActivity mDetailActivity;


    public detailActivityTest2(Class<DetailActivity> activityClass) {
        super(activityClass);
    }

    public detailActivityTest2(){
        super(DetailActivity.class);
    }


    public void testRead(){
//        mDetailActivity = getActivity();
//        InputStream in = null;
//        try {
//            in = mDetailActivity.getAssets().open("forest.jpg");
//            int lenght = in.available();
//            byte[]  buffer = new byte[lenght];
//
//            in.read(buffer);
//
//            String cKey = "1234567890abcDEF";
//            LocalDataProviderEncryptor ldEn = new LocalDataProviderEncryptor(cKey.getBytes());
//
//
//            byte [] encrpted = ldEn.encrypt(buffer);
//            byte [] decrpted = ldEn.decrypt(encrpted);
//            boolean result =  Arrays.equals(buffer, decrpted);
//
//            Assert.assertEquals(result, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (InvalidKeyException ke){
//            ke.printStackTrace();
//        }


    }

}
