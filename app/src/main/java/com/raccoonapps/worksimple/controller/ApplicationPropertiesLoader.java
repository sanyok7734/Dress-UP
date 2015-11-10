package com.raccoonapps.worksimple.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.Category;
import com.raccoonapps.worksimple.model.Coordinates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPropertiesLoader {

    private static ApplicationPropertiesLoader loader;
    private JSONObject properties;
    private Context context;

    public static ApplicationPropertiesLoader getLoader(Context context) {
        if (loader == null)
            loader = new ApplicationPropertiesLoader(context);
        return loader;
    }

    public enum BUTTON {
        BACK, EMAIL, FAQ, FACEBOOK, FORWARD, MORE, MUSIC, PHOTO, RESTART,
        SCROLL, SCROLL_PRESSED, START, TWITTER, WHATSAPP
    }

    public enum IMAGE {
        GIRL, GAME_BG, SPLASH, WELLDONE_BG, NINJA
    }

    public enum TRACK {
        MAIN
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            JSONArray categoriesFromJson = properties.getJSONArray("categories");
            for (int i = 0; i < categoriesFromJson.length(); i++) {
                JSONObject object = (JSONObject) categoriesFromJson.get(i);
                List<Accessory> accessories = new ArrayList<>();
                String categoryTitle = object.getString("category_title");
                String categoryIcon = object.getString("category_icon");
                String categoryIconPressed = object.getString("category_icon_pressed");
                JSONArray categoryResources = object.getJSONArray("category_resources");
                for (int j = 0; j < categoryResources.length(); j++) {
                    JSONObject accessoryObject = (JSONObject) categoryResources.get(j);
                    String fileName = accessoryObject.getString("file_name");
                    JSONArray coordinates = accessoryObject.getJSONArray("coordinates");
                    accessories.add(new Accessory
                            (new Coordinates(coordinates.getDouble(0), coordinates.getDouble(1)),
                                    context.getResources().getIdentifier("drawable/" + fileName.split("\\.")[0], null, context.getPackageName())));
                }
                Category category = new Category(accessories, categoryTitle, categoryIcon, categoryIconPressed);
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public int getImageIdByName(IMAGE image) {
        String imageName = null;
        try {
            JSONObject images = properties.getJSONObject("general_data").getJSONObject("images");
            imageName = images.getString(image.name().toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (imageName != null) ? context.getResources().getIdentifier("drawable/" + imageName.split("\\.")[0], null, context.getPackageName()) : 0;
    }

    public int getButtonIdByName(BUTTON button) {
        String buttonName = null;
        try {
            JSONObject buttons = properties.getJSONObject("general_data").getJSONObject("buttons");
            buttonName = buttons.getString(button.name().toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (buttonName != null) ? context.getResources().getIdentifier("drawable/" + buttonName.split("\\.")[0], null, context.getPackageName()) : 0;
    }

    public int getTrackIdByName(TRACK track) {
        String trackTitle = null;
        try {
            JSONObject sounds = properties.getJSONObject("general_data").getJSONObject("sounds");
            trackTitle = sounds.getString(track.name().toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (trackTitle != null) ? context.getResources().getIdentifier("raw/" + trackTitle.split("\\.")[0], null, context.getPackageName()) : 0;
    }
    private ApplicationPropertiesLoader(Context context) {
        this.context = context;
        try {
            properties = new JSONObject(convertInJson(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private String convertInJson(Context context) {
        InputStream stream = context.getResources().openRawResource(R.raw.resources);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder json = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null)
                json.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
