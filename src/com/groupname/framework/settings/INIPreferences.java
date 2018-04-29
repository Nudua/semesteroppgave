package com.groupname.framework.settings;

import com.groupname.framework.util.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is meant to store preferences in form of the ini file format.
 * String keys are associated by String values.
 *
 * Example ini file:
 * name=bob
 * age=22
 * city=Oslo
 */
public class INIPreferences {
    private static final String TRUE_AS_STRING = Boolean.toString(true);
    private static final String FALSE_AS_STRING = Boolean.toString(false);

    // The backing fileName used by this class as storage.
    private Path fileName;
    private Map<String, String> map;
    private boolean isDirty = false;

    /**
     * Creates a new instance and attempts to parse and
     * load the keypair values from the file specified if it exists.
     *
     * @param fileName the file to use as a backing store for this instance.
     * @throws IOException if there was an issue loading in the file given.
     */
    public INIPreferences(Path fileName) throws IOException {
        this.fileName = Objects.requireNonNull(fileName);

        map = new HashMap<>();

        if(Files.exists(fileName)) {
            load(fileName);
        }
    }

    // Load and parse an ini file
    private void load(Path fileName) throws IOException {
        try(BufferedReader reader = Files.newBufferedReader(fileName)) {
            String line = "";

            // Matches lines with the format:
            // myKey=Value
            Pattern validLine = Pattern.compile("^(?<key>[a-z]+)=(?<value>[a-zA-Z0-9-.]+)$");

            // Read the file line by line
            while((line = reader.readLine()) != null) {
                if(line.startsWith(";")) { // Ignore empty and lines that start with ;
                    continue;
                }

                // Remove any whitespace
                line = line.trim();

                // Continue if our line is empty
                if("".equals(line)) {
                    continue;
                }

                Matcher matcher = validLine.matcher(line);

                if(!matcher.matches() || matcher.groupCount() != 2) {
                    System.err.println("Unable to parse line: " + line);
                    continue;
                }

                String key = matcher.group("key");
                String value = matcher.group("value");

                map.put(key, value);
            }
        }
    }

    private String validateAndGetLowerCaseKey(String key) {
        Strings.requireNonNullAndNotEmpty(key);
        return key.toLowerCase(Locale.ROOT);
    }

    /**
     * Puts or updates the supplied key and value.
     * Note: the key may not be null or equal to "", all keys are transformed to lowercase using the Locale.ROOT locale.
     *
     * @param key the key to put.
     * @param value the value to put.
     */
    public void put(String key, String value) {
        Strings.requireNonNullAndNotEmpty(key);
        if(value == null) {
            value = Strings.EMPTY;
        }

        map.put(key.toLowerCase(Locale.ROOT), value);
        isDirty = true;
    }

    /**
     * Puts or updates the supplied key and boolean value.
     * Note: the key may not be null or equal to "", all keys are transformed to lowercase using the Locale.ROOT locale.
     *
     * @param key the key to put.
     * @param value the boolean value to put.
     */
    public void putBoolean(String key, boolean value) {
        String valueAsString = value ? TRUE_AS_STRING : FALSE_AS_STRING;

        put(key, valueAsString);
    }

    /**
     * Puts or updates the supplied key and double value.
     * Note: the key may not be null or equal to "", all keys are transformed to lowercase using the Locale.ROOT locale.
     *
     * @param key the key to put.
     * @param value the double value to put.
     */
    public void putDouble(String key, double value) {
        String doubleAsString = Double.toString(value);

        put(key, doubleAsString);
    }

    /**
     * Puts or updates the supplied key and integer value.
     * Note: the key may not be null or equal to "", all keys are transformed to lowercase using the Locale.ROOT locale.
     *
     * @param key the key to put.
     * @param value the integer value to put.
     */
    public void putInt(String key, int value) {
        put(key, Integer.toString(value));
    }

    /**
     * Gets the value associated by the key supplied if the key exists.
     * Note: returns an "" (Empty) String if the key was not found.
     *
     * @param key the key used to retrieve the value.
     * @return the value associated by the key supplied, returns an "" (Empty) String if the key was not found.
     */
    public String get(String key) {
        String lowerCaseKey = validateAndGetLowerCaseKey(key);

        if(map.containsKey(lowerCaseKey)) {
            return map.get(lowerCaseKey);
        }

        return Strings.EMPTY;
    }

    /**
     * Gets the boolean value associated by the key supplied if the key exists and if the value is a valid integer,
     * otherwise the defaultValue will be returned.
     *
     * @param key the key used to retrieve the value.
     * @param defaultValue this value will be returned if the key does not exist.
     * @return the boolean value associated by the key supplied, returns the defaultValue if no such association exists.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String lowerCaseKey = validateAndGetLowerCaseKey(key);

        if(map.containsKey(lowerCaseKey)) {
            String value = map.get(lowerCaseKey);

            if(value.equalsIgnoreCase(TRUE_AS_STRING)) {
                return true;
            } else if(value.equalsIgnoreCase(FALSE_AS_STRING)) {
                return false;
            }
        }
        return defaultValue;
    }

    /**
     * Gets the double value associated by the key supplied if the key exists and if the value is a valid integer,
     * otherwise the defaultValue will be returned.
     *
     * @param key the key used to retrieve the value.
     * @param defaultValue this value will be returned if the key does not exist.
     * @return the double value associated by the key supplied, returns the defaultValue if no such association exists.
     *         or if there was an issue when trying to parse the value.
     */
    public double getDouble(String key, double defaultValue) {
        String lowerCaseKey = validateAndGetLowerCaseKey(key);

        if(map.containsKey(lowerCaseKey)) {
            String value = map.get(lowerCaseKey);

            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException exception) {
                System.err.println("Unable to parse double: " + exception.getMessage());
            }

        }
        return defaultValue;
    }

    /**
     * Gets the integer value associated by the key supplied if the key exists and if the value is a valid integer,
     * otherwise the defaultValue will be returned.
     *
     * @param key the key used to retrieve the value.
     * @param defaultValue this value will be returned if the key does not exist.
     * @return the integer value associated by the key supplied, returns the defaultValue if no such association exists.
     */
    public int getInt(String key, int defaultValue) {
        String lowerCaseKey = validateAndGetLowerCaseKey(key);

        if(map.containsKey(lowerCaseKey)) {
            String value = map.get(lowerCaseKey);

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.err.println("Unable to parse number: " + ex.getMessage());
            }
        }
        return defaultValue;
    }

    /**
     * Writes the keypair values in the form of a ini file format to the file specified by the constructor.
     *
     * @throws IOException if there was an issue writing to the target file.
     */
    public void write() throws IOException {
        // No pairs to write
        if(map.size() == 0 || !isDirty) {
            System.out.println("preferences is already updated..");
            return;
        }

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(fileName, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE))
        {
            for(Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // Writes the current pair value in the format of 'key=value'
                bufferedWriter.write(String.format("%s=%s\n", key, value));
            }
        }

        isDirty = false;
    }

    /**
     * Gets the String representation of this object.
     *
     * fileName is the path used for storing the file on disk.
     * map holds the contents of the preferences.
     * isDirty says if the collection has been changed or not since it was last stored to the disk.
     *
     * @return
     */
    @Override
    public String toString() {
        return "INIPreferences{" +
                "fileName=" + fileName +
                ", map=" + map +
                ", isDirty=" + isDirty +
                '}';
    }
}
