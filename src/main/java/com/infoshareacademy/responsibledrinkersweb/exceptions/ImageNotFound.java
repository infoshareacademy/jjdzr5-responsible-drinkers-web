package com.infoshareacademy.responsibledrinkersweb.exceptions;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageNotFound {

    public static String verifyURL(String urlImg) {
        try {
            URL url = new URL(urlImg);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception(String.valueOf(responseCode));
            }
        } catch (Exception e) {
            return "";
        }
        return urlImg;
    }
}
