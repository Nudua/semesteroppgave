package com.groupname.framework.serial;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.W32APIOptions;

public class LibraryUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Library> T loadDll(String dllName, Class<? extends Library> c) {
        T library = null;
        try {
            if(Platform.isWindows()) {
                library = (T)Native.loadLibrary(dllName, c, W32APIOptions.UNICODE_OPTIONS);
            } else {
                library = (T)Native.loadLibrary(dllName, c);
            }
        }
        catch (UnsatisfiedLinkError error) {
            System.out.println(String.format("Unable to load: %s", dllName));
        } catch (ClassCastException exception) {
            System.out.println(String.format("Unable to cast library to target class: %s", c.getName()));
        }

        return library;
    }
}
