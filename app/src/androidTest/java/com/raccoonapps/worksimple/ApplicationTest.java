package com.raccoonapps.worksimple;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.FlakyTest;
import android.util.Log;

import com.raccoonapps.worksimple.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.BUTTON;
import static com.raccoonapps.worksimple.controller.ApplicationPropertiesLoader.getLoader;

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
        List<Category> categories = getLoader(getContext()).getAllCategories();
        assertNotNull(categories);
        int buttonId = getLoader(getContext()).getButtonIdByName(BUTTON.MORE);
        assertNotSame(0, buttonId);
    }

    @FlakyTest
    public void testGenerateJsonResource() throws JSONException {
        String categoryTitle = "shoes";
        String categoryIcon = categoryTitle + "_ic.png";
        int accessoriesCount = 11;

        JSONObject category = new JSONObject();
        category.put("category_title", categoryTitle);
        category.put("category_icon", categoryIcon);

        JSONArray accessories = new JSONArray();
        for (int i = 0; i < accessoriesCount; i++) {
            JSONObject accessory = new JSONObject();
            accessory.put("file_name", categoryTitle + (i + 1) + ".png");
            accessory.put("coordinates", new JSONArray());
            accessories.put(accessory);
        }

        category.put("category_resources", accessories);
        Log.d("JSON", category.toString());
    }


}