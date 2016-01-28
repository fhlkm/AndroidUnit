package com.example.hanlufeng.instrumentunit;

import android.content.Context;
import android.os.Environment;



import java.io.File;
import java.io.IOException;

/**
 * Created by hanlu.feng on 1/19/2016.
 */
public class FileUtil {


    private static final String TAG = FileUtil.class.getSimpleName();
    public enum LocationOption {
        INTERNAL_ONLY
        ,
        USE_BOTH_INTERNAL_FIRST
        ,
        USE_BOTH_EXTERNAL_FIRST
    }






    public static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        boolean storageAvailable = false;

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            storageAvailable = true;
        } else {
            storageAvailable = false;
        }

        return storageAvailable;
    }

    public static File getFilesInternalDirectory(Context context) {
        File filesDir = context.getFilesDir();

        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }

        return filesDir;
    }

    public static File getFilesDirectory(Context context) {
        return getFilesDirectory(context, LocationOption.USE_BOTH_EXTERNAL_FIRST);
    }

    public static File getFilesDirectory(Context context, LocationOption storageType) {
        File filesDir = null;

        switch (storageType){
            case INTERNAL_ONLY:
                filesDir = context.getFilesDir();
                break;
            case USE_BOTH_EXTERNAL_FIRST:
                filesDir = context.getExternalFilesDir(null);
                if (filesDir == null) {
                    filesDir = context.getFilesDir();
                }
                break;
            case USE_BOTH_INTERNAL_FIRST:
                filesDir = context.getFilesDir();
                if (filesDir == null) {
                    filesDir = context.getExternalFilesDir(null);
                }
                break;
            default:
                break;
        }

        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }

        return filesDir;
    }

    public static File getCacheDirectory(Context context, LocationOption storageType) {
        File cacheDir = null;

        switch (storageType){
            case INTERNAL_ONLY:
                cacheDir = context.getCacheDir();
                break;
            case USE_BOTH_EXTERNAL_FIRST:
                cacheDir = context.getExternalCacheDir();
                if (cacheDir == null) {
                    cacheDir = context.getCacheDir();
                }
                break;
            case USE_BOTH_INTERNAL_FIRST:
                cacheDir = context.getCacheDir();
                if (cacheDir == null) {
                    cacheDir = context.getExternalCacheDir();
                }
                break;
            default:
                break;
        }

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        return cacheDir;
    }

    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, LocationOption.USE_BOTH_EXTERNAL_FIRST);
    }
}
