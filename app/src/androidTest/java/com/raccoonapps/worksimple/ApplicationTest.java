package com.raccoonapps.worksimple;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.FlakyTest;
import android.util.Log;

import com.raccoonapps.worksimple.model.ApplicationPropertiesLoader;
import com.raccoonapps.worksimple.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @FlakyTest
    public void testPropertiesLoader() throws Exception {
        assertNotNull(getContext());
        List<Category> categories = ApplicationPropertiesLoader.getLoader(getContext()).getAllCategories();
        assertNotNull(categories);
        int buttonId = ApplicationPropertiesLoader.getLoader(getContext()).getButtonIdByName(ApplicationPropertiesLoader.BUTTON.MORE);
        assertNotSame(0, buttonId);
    }

    @FlakyTest
    public void testGenerateJsonResource() throws JSONException {
        String categoryTitle = "shoes";
        String categoryIcon = categoryTitle + "_ic.png";

        JSONObject category = new JSONObject();
        category.put("category_title", categoryTitle);
        category.put("category_icon", categoryIcon);

        JSONArray accessories = new JSONArray();
        for (int i = 0; i < 11; i++) {
            JSONObject accessory = new JSONObject();
            accessory.put("file_name", categoryTitle + (i + 1) + ".png");
            accessory.put("coordinates", new JSONArray());
            accessories.put(accessory);
        }

        category.put("category_resources", accessories);
        Log.d("JSON", category.toString());
    }

}