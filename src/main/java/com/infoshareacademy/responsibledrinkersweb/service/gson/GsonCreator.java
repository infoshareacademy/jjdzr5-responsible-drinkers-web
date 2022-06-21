package com.infoshareacademy.responsibledrinkersweb.service.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonCreator {
    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .setPrettyPrinting()
                .create();
    }

}
