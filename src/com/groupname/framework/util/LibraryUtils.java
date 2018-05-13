package com.groupname.framework.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.W32APIOptions;

/**
 * Helper class which is meant for loading native libraries using JNA (Java Native Access).
 */
public final class LibraryUtils {

    /**
     * Attempts to the load the specified native library and cast it into the wanted class.
     * Null will be returned if there was an issue loading the library.
     * The given class must be a subclass of the Library class.
     *
     * @param libName the name of the library to load. (usually a dll file in Windows)
     * @param clazz the class to cast this library into. Must subclass Library.
     * @param <T> the instance class type of the wanted library.
     * @return an instance of the wanted library if successful, otherwise null will be returned.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Library> T load(String libName, Class<? extends Library> clazz) {
        T library = null;
        try {
            if(Platform.isWindows()) {
                library = (T)Native.loadLibrary(libName, clazz, W32APIOptions.UNICODE_OPTIONS);
            } else {
                library = (T)Native.loadLibrary(libName, clazz);
            }
        }
        catch (UnsatisfiedLinkError error) {
            System.err.println(String.format("Unable to load: %s", libName));
        } catch (ClassCastException exception) {
            System.err.println(String.format("Unable to cast library to target class: %s", clazz.getName()));
        }

        return library;
    }
}
