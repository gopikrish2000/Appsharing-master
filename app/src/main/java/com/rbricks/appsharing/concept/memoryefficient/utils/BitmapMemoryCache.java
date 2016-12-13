package com.rbricks.appsharing.concept.memoryefficient.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by gopikrishna on 13/12/16.
 */
public class BitmapMemoryCache {
    private static BitmapMemoryCache instance;
    private LruCache<String, Bitmap> bitmapMemoryCache;


    public static BitmapMemoryCache getInstance() {
        if (instance == null) {
            instance = new BitmapMemoryCache();
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            instance.bitmapMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
        return instance;
    }

    private BitmapMemoryCache() {
    }

    public void putBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            bitmapMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return bitmapMemoryCache.get(key);
    }
}
