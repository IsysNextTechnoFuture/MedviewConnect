package com.isysnext.medviewmd.medviewconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isysnext.medviewmd.medviewconnect.modelDr.HomeProviderListDTO;
import com.isysnext.medviewmd.medviewconnect.modelDr.UserDTO;


import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh on 10/04/18.
 */
public class AppSession {
    //Declaration of variables
    private static final String SESSION_NAME = "com.kavya.silvermart";
    private static final String APP_DEFAULT_LANGUAGE = "en";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;
    Map<String,String> pendingNotificationMap;
    List<Map<String,String>> pendingNotificationList;

    //Constructor
    public AppSession(Context context) {
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME,
                Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }

    public String getLanguage() {
        /** en= English ar=Arabic */
        return mSharedPreferences.getString("getLanguage", APP_DEFAULT_LANGUAGE);
    }

    public UserDTO getUser() {
        String userJSONString = mSharedPreferences.getString("getUser", "");
        if (userJSONString == null)
            return null;
        Type type = new TypeToken<UserDTO>() {
        }.getType();
        UserDTO userDTO = new Gson().fromJson(userJSONString, type);
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        prefsEditor = mSharedPreferences.edit();
        if (userDTO == null)
            prefsEditor.putString("getUser", null);
        else {
            String userJSONString = new Gson().toJson(userDTO);
            prefsEditor.putString("getUser", userJSONString);
        }
        prefsEditor.commit();
    }


    public boolean isLogin() {
        return mSharedPreferences.getBoolean("Login", false);
    }

    public void setLogin(boolean Login) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("Login", Login);
        prefsEditor.commit();
    }


    public int getNotification() {
        return mSharedPreferences.getInt("notification", 0);

    }

    public void setNotification(int notification) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putInt("notification", notification);
        prefsEditor.commit();
    }


    public String getPassword() {
        return mSharedPreferences.getString("getPassword", "");
    }

    public void setPassword(String password) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getPassword", password);
        prefsEditor.commit();
    }



    public String getLoginId() {
        return mSharedPreferences.getString("getLoginId", "");
    }

    public void setLoginId(String loginId) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getLoginId", loginId);
        prefsEditor.commit();
    }
    //for image capture
    public Uri getImageUri() {
        String imageUri = mSharedPreferences.getString("getImageUri", "");
        if (imageUri == null || imageUri.equals(""))
            return null;
        return Uri.parse(imageUri);
    }

    public void setImageUri(Uri imageUri) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImageUri", imageUri.toString());
        prefsEditor.commit();
    }

    public String getImagePath() {
        return mSharedPreferences.getString("getImagePath", "");
    }

    public void setImagePath(String imagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getImagePath", imagePath);
        prefsEditor.commit();
    }

    public String getCropImagePath() {
        return mSharedPreferences.getString("getCropImagePath", "");
    }

    public void setCropImagePath(String cropImagePath) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("getCropImagePath", cropImagePath);
        prefsEditor.commit();
    }


    public String getFilter() {
        return mSharedPreferences.getString("filter", "");
    }

    public void setFilter(String json) {
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("filter", json);
        prefsEditor.commit();
    }

    //For Merging
    public void setNotification(Boolean value) {
        prefsEditor.putBoolean("Notification", value);
        prefsEditor.commit();
    }

    public boolean isNotification() {
        return mSharedPreferences.getBoolean("Notification", true);
    }


    public HomeProviderListDTO getProviderDetails() {
        String userJSONString = mSharedPreferences.getString("getDetails", "");
        if (userJSONString == null)
            return null;
        Type type = new TypeToken<HomeProviderListDTO>() {
        }.getType();
        HomeProviderListDTO homeProviderListDTO = new Gson().fromJson(userJSONString, type);
        return homeProviderListDTO;
    }

    public void setProviderDetails(HomeProviderListDTO homeProviderListDTO) {
        prefsEditor = mSharedPreferences.edit();
        if (homeProviderListDTO == null)
            prefsEditor.putString("getDetails", null);
        else {
            String userJSONString = new Gson().toJson(homeProviderListDTO);
            prefsEditor.putString("getDetails", userJSONString);
        }
        prefsEditor.commit();
    }

}
