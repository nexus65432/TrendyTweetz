package com.nexus.trendytweetz.datautils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexus.trendytweetz.datamodel.StatusesItem;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Specifies additional type converters that Room can use.
 * Converts List<StatusesItem> to String and vice versa to be used convert object
 */
public class StatusesConverter {

    @TypeConverter
    public String fromStatusesList(List<StatusesItem> statusesItems) {
        if (statusesItems == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<StatusesItem>>() {
        }.getType();
        String json = gson.toJson(statusesItems, type);
        return json;
    }

    @TypeConverter
    public List<StatusesItem> toStatusesList(String statusesItemsString) {
        if (statusesItemsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<StatusesItem>>() {
        }.getType();
        List<StatusesItem> StatusesList = gson.fromJson(statusesItemsString, type);
        return StatusesList;
    }
}
