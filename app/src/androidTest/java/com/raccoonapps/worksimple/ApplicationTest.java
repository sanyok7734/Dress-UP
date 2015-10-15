package com.raccoonapps.worksimple;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.raccoonapps.worksimple.model.ApplicationPropertiesLoader;
import com.raccoonapps.worksimple.model.Category;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testPropertiesLoader() throws Exception {
        assertNotNull(getContext());
        List<Category> categories = ApplicationPropertiesLoader.getLoader(getContext()).getAllCategories();
        assertNotNull(categories);
    }
}