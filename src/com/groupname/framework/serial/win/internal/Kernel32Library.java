package com.groupname.framework.serial.win.internal;

import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinBase;
import com.sun.jna.ptr.IntByReference;

import static  com.sun.jna.platform.win32.WinNT.HANDLE;

public interface Kernel32Library extends Library {

    // Kernel32.dll
    // Gets the  current status of the selected serial  port
    boolean GetCommState(HANDLE handle, DCB lpDCB);

    // Sets the current status of the selected serial  port
    boolean SetCommState(HANDLE handle, DCB lpDCB);

    HANDLE CreateFile(String lpFileName, long dwDesiredAccess, int dwShareMode, WinBase.SECURITY_ATTRIBUTES lpSecurityAttributes, int dwCreationDisposition, int dwFlagsAndAttributes, HANDLE lpName);

    boolean CloseHandle(HANDLE handle);

    boolean ReadFile(HANDLE handle, byte[] lpBuffer, int nNumberOfBytesToRead, IntByReference lpNumberOfBytesRead, WinBase.OVERLAPPED lpOverlapped);
    //boolean WriteFile(HANDLE handle, byte[] lpBuffer, int nNumberOfBytesToWrite, IntByReference lpNumberOfBytesWritten, WinBase.OVERLAPPED lpOverlapped);

    boolean BuildCommDCB(String settings, DCB dcb);
}
