package com.rbricks.appsharing.utils;

import com.rbricks.appsharing.concept.Application.AppSharingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gopikrishna on 31/01/17.
 */

public class JsonReaderFromAssets {

    public String getDefaultConfigData() {

        StringBuilder buf = new StringBuilder();
        InputStream json = null;
        try {
            json = AppSharingApplication.getInstance().getAssets().open("default_config.json");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf.toString();
    }
}
