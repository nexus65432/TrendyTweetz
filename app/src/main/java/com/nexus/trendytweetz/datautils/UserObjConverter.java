package com.nexus.trendytweetz.datautils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexus.trendytweetz.entity.User;

import java.lang.reflect.Type;

/**
 * Specifies additional type converters that Room can use.
 * Converts User to String and vice versa to be used convert object
 */
public class UserObjConverter {

    @TypeConverter
    public static String convertUserToString(User userObj) {
        if (userObj == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        String json = gson.toJson(userObj, type);
        return json;
    }

    @TypeConverter
    public User toStatusesList(String userObjString) {
        if (userObjString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        User userObj = gson.fromJson(userObjString, type);
        return userObj;
    }

}
