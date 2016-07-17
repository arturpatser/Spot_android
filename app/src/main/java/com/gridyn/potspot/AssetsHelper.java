package com.gridyn.potspot;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class AssetsHelper {

    public static Drawable loadImageFromAsset(Context context, String imagePath) {
        try {
            InputStream is = context.getAssets().open(imagePath);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
