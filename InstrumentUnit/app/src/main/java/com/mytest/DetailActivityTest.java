package com.mytest;

import android.test.InstrumentationTestCase;

import junit.framework.Assert;

/**
 * Created by hanlu.feng on 1/21/2016.
 */
public class DetailActivityTest extends InstrumentationTestCase {
    public void test(){
        int a =5;
        int b =5;
        Assert.assertEquals(a, b);
    }

}