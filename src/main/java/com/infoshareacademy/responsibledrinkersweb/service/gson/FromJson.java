package com.infoshareacademy.responsibledrinkersweb.service.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoshareacademy.responsibledrinkersweb.domain.Count;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FromJson {

    public static List<Count> getListOfCount(String json) {
        Gson gson = GsonCreator.getGson();
        Type typeToken = new TypeToken<List<Count>>() {
        }.getType();
        return (List<Count>) Optional.ofNullable(gson.fromJson(json, typeToken)).orElse(new ArrayList<>());
    }

    private FromJson() {
    }

}
