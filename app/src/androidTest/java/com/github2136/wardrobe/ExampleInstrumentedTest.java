package com.github2136.wardrobe;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = androidx.test.InstrumentationRegistry.getTargetContext();

        assertEquals("com.github2136.wardrobe", appContext.getPackageName());
    }
}
