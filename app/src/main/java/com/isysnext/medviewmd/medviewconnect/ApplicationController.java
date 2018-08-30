package com.isysnext.medviewmd.medviewconnect;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.isysnext.medviewmd.medviewconnect.modelDr.MyMessageDTO;

public class ApplicationController extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(MyMessageDTO.class);

       /* TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "font/oswalt.ttf"))
                .create();
        TypefaceHelper.init(typeface);*/
        ActiveAndroid.initialize(this);
    }
}
