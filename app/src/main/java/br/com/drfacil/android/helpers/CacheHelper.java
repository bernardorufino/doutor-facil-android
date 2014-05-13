package br.com.drfacil.android.helpers;

import android.content.Context;

import java.io.File;

public class CacheHelper {

    public static File createCacheDir(Context context, String id) {
        File root = context.getCacheDir();
        File dir = new File(root, id);
        if (!dir.mkdir() && !dir.isDirectory()) return null;
        return dir;
    }

    // Prevents instantiation
    private CacheHelper() {
        throw new AssertionError("Cannot instantiate object from " + this.getClass());
    }
}
