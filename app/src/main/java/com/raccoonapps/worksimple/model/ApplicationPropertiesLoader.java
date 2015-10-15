package com.raccoonapps.worksimple.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.raccoonapps.worksimple.R;

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

    private ApplicationPropertiesLoader(Context context) throws Exception {
        this.context = context;
        properties = new JSONObject(convertInJson(context));
    }

    @NonNull
    private String convertInJson(Context context) throws IOException {
        InputStream stream = context.getResources().openRawResource(R.raw.resources);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            json.append(line);
        return json.toString();
    }

    public static ApplicationPropertiesLoader getLoader(Context context) throws Exception {
        if (loader == null)
            loader = new ApplicationPropertiesLoader(context);
        return loader;
    }

    public List<Category> getAllCategories() throws JSONException {
        List<Category> categories = new ArrayList<>();
        JSONArray categoriesFromJson = properties.getJSONArray("categories");
        for (int i = 0; i < categoriesFromJson.length(); i++) {
            JSONObject object = (JSONObject) categoriesFromJson.get(i);
            List<Accessory> accessories = new ArrayList<>();
            String categoryTitle = object.getString("category_title");
            String categoryIcon = object.getString("category_icon");
            int layer = object.getInt("layer");
            JSONArray categoryResources = object.getJSONArray("category_resources");
            for (int j = 0; j < categoryResources.length(); j++) {
                JSONObject accessoryObject = (JSONObject) categoryResources.get(i);
                String fileName = accessoryObject.getString("file_name");
                JSONArray coordinates = accessoryObject.getJSONArray("coordinates");
                accessories.add(new Accessory
                        (new Coordinates(coordinates.getDouble(0), coordinates.getDouble(1)),
                                context.getResources().getIdentifier("drawable/" + fileName.split("\\.")[0], null, context.getPackageName())));
            }
            Category category = new Category(accessories, categoryTitle, categoryIcon, layer);
            categories.add(category);
        }
        return categories;
    }

}
