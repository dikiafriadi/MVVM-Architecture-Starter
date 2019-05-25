package com.aditp.mdvkarch.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPref {

    /**
     * -------------------------------------------------
     * AUTHOR       : ADITYA PRATAMA
     * YEAR         : 2018
     * Singleton SharedPreference
     * -------------------------------------------------
     * using example :
     * <p>
     * GET DATA
     * object = SharedPref.getInstance().getString(KEY_USERNAME, "");
     * <p>
     * SAVE DATA
     * SharedPref.getInstance().saveString(object);
     */

    private static SharedPref instance;
    private static SharedPreferences mSharedPreferences;

    private SharedPref() {
    }

    // biar bisa di init langsung di application
    public static void init(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // singleton technique kakah plus with synchronized ~
    public static SharedPref getInstance() {
        if (instance == null) {
            validateInitialization();
            synchronized (SharedPref.class) {
                if (instance == null) {
                    instance = new SharedPref();
                }
            }
        }
        return instance;
    }

    private static void validateInitialization() {
        if (mSharedPreferences == null)
            throw new RuntimeException(" init this class to your fucking extends application class ~");
    }

    public void saveInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        if (isKeyExists(key)) {
            return mSharedPreferences.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (isKeyExists(key)) {
            return mSharedPreferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveFloat(String key, float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultValue) {
        if (isKeyExists(key)) {
            return mSharedPreferences.getFloat(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defaultValue) {
        if (isKeyExists(key)) {
            return mSharedPreferences.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        if (isKeyExists(key)) {
            return mSharedPreferences.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public <T> void saveObject(String key, T object) {
        String objectString = new Gson().toJson(object);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, objectString);
        editor.apply();
    }

    public <T> T getObject(String key, Class<T> classType) {
        if (isKeyExists(key)) {
            String objectString = mSharedPreferences.getString(key, null);
            if (objectString != null) {
                return new Gson().fromJson(objectString, classType);
            }
        }
        return null;
    }

    public <T> void saveObjectsList(String key, List<T> objectList) {
        String objectString = new Gson().toJson(objectList);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, objectString);
        editor.apply();
    }

    public <T> List<T> getObjectsList(String key, Class<T> classType) {
        if (isKeyExists(key)) {
            String objectString = mSharedPreferences.getString(key, null);
            if (objectString != null) {

                ArrayList<T> t = new Gson().fromJson(objectString, new TypeToken<List<T>>() {
                }.getType());

                List<T> finalList = new ArrayList<>();

                for (int i = 0; i < t.size(); i++) {
                    String s = String.valueOf(t.get(i));
                    finalList.add(new Gson().fromJson(s, classType));
                }

                return finalList;
            }
        }

        return null;
    }

    public void clearSession() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean deleteValue(String key) {
        if (isKeyExists(key)) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.apply();
            return true;
        }

        return false;
    }

    public boolean isKeyExists(String key) {
        Map<String, ?> map = mSharedPreferences.getAll();
        if (map.containsKey(key)) {
            return true;
        } else {
            Log.e("SharedPref", "No element founded in sharedPrefs with the key " + key);
            return false;
        }
    }

}
