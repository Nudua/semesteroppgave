package com.groupname.framework.serial.win.internal;

import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.ptr.IntByReference;

import static  com.sun.jna.platform.win32.WinNT.HANDLE;


/**
 * Preface: We're using JNA (Java Native Access) to map native Win32 dll calls to an Java interface that is easy to use.
 *
 * This interface is used make native calls to the kernel32.dll, in this project it's primary use is to build up a DCB (device control block)
 * structure to perform communications over the serial port.
 *
 * All these methods are automagically mapped by the JNA library to the corresponding native methods in the kernel32 library.
 *
 * Getting an actual instance of this library, you can use the LibraryUtils to load it. (Only supported on Windows)
 *
 * Example:
 * Kernel32Library library = LibraryUtils.loadDll(Kernel32Library.DLL_NAME, Kernel32Library.class);
 *
 * Note: library will be null if we failed to load the library (probably on an unsupported platform)
 */
public interface Kernel32Library extends Library {

    /**
     * The Win32 dll name of this library.
     */
    String DLL_NAME = "kernel32.dll";

    /**
     * Updates the DCB (communications) structure for the selected file handle.
     * Use CreateFile to obtain a valid Win32 Handle.
     *
     * @param handle a Win32 handle to the communications device.
     * @param lpDCB a reference to the DCB structure used for storing the result.
     * @return true if successful, false otherwise.
     */
    boolean GetCommState(HANDLE handle, DCB lpDCB);

    // Sets the current status of the selected serial  port

    /**
     * Configures the communications device with the values specified in the DCB structure (device control block).
     *
     * @param handle a Win32 handle to the communications device.
     * @param lpDCB the DCB structure used for configuring the device.
     * @return true if successful, false otherwise.
     */
    boolean SetCommState(HANDLE handle, DCB lpDCB);

    HANDLE CreateFile(String lpFileName, long dwDesiredAccess, int dwShareMode, WinBase.SECURITY_ATTRIBUTES lpSecurityAttributes, int dwCreationDisposition, int dwFlagsAndAttributes, HANDLE lpName);

    /**
     * Closes a open handle.
     *
     * @param handle a valid Win32 handle to an open object.
     * @return true if the handle was closed, false otherwise.
     */
    boolean CloseHandle(HANDLE handle);

    /**
     * Attempt to read from a file or i/o device, can also be used for reading from various communication devices such as a serial port.
     *
     * @param handle the handle to read from.
     * @param lpBuffer the byte buffer to read into.
     * @param nNumberOfBytesToRead the requested amount of bytes we want to read from the handle.
     * @param lpNumberOfBytesRead the amount of bytes actually read will be set on this value.
     * @param lpOverlapped a reference to a lpOverlapped structure if required. Not required for serial port communications.
     * @return true if we were successful in reading, false otherwise.
     */
    boolean ReadFile(HANDLE handle, byte[] lpBuffer, int nNumberOfBytesToRead, IntByReference lpNumberOfBytesRead, WinBase.OVERLAPPED lpOverlapped);

    /**
     * This function will parse the definitions String and set the value corresponding in the DCB structure.
     *
     * Example:
     * baud=9600 parity=N data=8 stop=1
     *
     * Will specify a baud rate of 9600, parity to none with 8 databits and 1 stop bit.
     *
     * @param definitions the String to parse for DCB definitions.
     * @param dcb the DCB structure to update based on the definitions.
     * @return true if valid definitions were given and there were no other issues, false if there were any problems.
     */
    boolean BuildCommDCB(String definitions, DCB dcb);
}
