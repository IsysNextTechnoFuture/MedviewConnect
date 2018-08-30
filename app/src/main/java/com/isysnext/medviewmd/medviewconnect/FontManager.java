package com.isysnext.medviewmd.medviewconnect;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {
    public static final String ROOT = "font/",
         //   FONTAWESOME = ROOT + "fontawesome.ttf";
    FONTAWESOME = ROOT + "FontAwesome.ttf";
    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}