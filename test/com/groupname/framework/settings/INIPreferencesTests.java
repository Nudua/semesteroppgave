package com.groupname.framework.settings;

import com.groupname.framework.util.EmptyStringException;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

import static org.junit.Assert.*;

public class INIPreferencesTests {

    private final static String TEST_FILENAME = "testsettings.ini";

    @Test(expected = NullPointerException.class)
    public void constructorFileNameThrowsOnNull() throws IOException {
        new INIPreferences(null);
    }

    @Test
    public void keyIsCaseInsensitive() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String key = "KeY";
        String value = "test";

        preferences.put(key, value);

        assertEquals(preferences.get(key), value);
        assertEquals(preferences.get(key.toUpperCase(Locale.ROOT)), value);
        assertEquals(preferences.get(key.toLowerCase(Locale.ROOT)), value);
    }

    @Test(expected = NullPointerException.class)
    public void putKeyCannotBeNull() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String value = "test";

        preferences.put(null, value);
    }

    @Test(expected = EmptyStringException.class)
    public void putKeyCannotBeEmpty() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        preferences.put("", "test");
    }

    @Test
    public void putPutsValueCorrectly() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String key = "key";
        String value = "test";

        preferences.put(key, value);

        assertEquals(preferences.get(key), value);
    }

    @Test(expected = NullPointerException.class)
    public void putIntKeyCannotBeNull() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        preferences.putInt(null, 1);
    }

    @Test(expected = EmptyStringException.class)
    public void putIntKeyCannotBeEmpty() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        preferences.putInt("", 1);
    }

    @Test
    public void putIntPutsValueCorrectly() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String key = "key";
        int value = 23;

        preferences.putInt(key, value);

        assertEquals(preferences.getInt(key, 0), value);
    }

    @Test(expected = NullPointerException.class)
    public void putBooleanKeyCannotBeNull() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        preferences.putBoolean(null, false);
    }

    @Test(expected = EmptyStringException.class)
    public void putBooleanKeyCannotBeEmpty() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        preferences.putBoolean("", false);
    }

    @Test
    public void putBooleanPutsValueCorrectly() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String key = "key";
        boolean value = true;

        preferences.putBoolean(key, value);

        assertEquals(preferences.getBoolean(key, false), value);
    }

    @Test(expected = NullPointerException.class)
    public void getBooleanKeyCannotBeNull() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));
        preferences.get(null);
    }

    @Test(expected = EmptyStringException.class)
    public void getBooleanKeyCannotBeEmpty() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));
        preferences.get("");
    }

    @Test
    public void savesAndRestoresPreferencesCorrectly() throws IOException {
        INIPreferences preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        String key = "key";
        String value = "Hello";

        preferences.put(key, value);
        preferences.write();

        preferences = new INIPreferences(Paths.get(TEST_FILENAME));

        assertEquals(preferences.get(key), value);
    }
}
