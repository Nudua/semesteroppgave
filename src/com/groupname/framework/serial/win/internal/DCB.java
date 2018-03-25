package com.groupname.framework.serial.win.internal;

import com.sun.jna.Structure;
import static com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;

public class DCB extends Structure {

    public int DCBlength;
    public int BaudRate;
    public int fBinary ;
    public int fParity ;
    public int fOutxCtsFlow ;
    public int fOutxDsrFlow ;
    public int fDtrControl ;
    public int fDsrSensitivity ;
    public int fTXContinueOnXoff;
    public int fOutX ;
    public int fInX ;
    public int fErrorChar;
    public int fNull;
    public int fRtsControl;
    public int fAbortOnError;
    public int fDummy2;
    public short wReserved;
    public short XonLim;
    public short XoffLim;
    public byte ByteSize;
    public byte Parity;
    public byte StopBits;
    public CHAR  XonChar;
    public CHAR  XoffChar;
    public CHAR  ErrorChar;
    public CHAR  EofChar;
    public CHAR  EvtChar;
    public short wReserved1;

    @SuppressWarnings("rawtypes")
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("DCBlength", "BaudRate", "fBinary", "fParity", "fOutxCtsFlow",
                "fOutxDsrFlow", "fDtrControl","fDsrSensitivity","fTXContinueOnXoff","fOutX",
                "fInX","fErrorChar","fNull","fRtsControl","fAbortOnError","fDummy2","wReserved","XonLim","XoffLim",
                "ByteSize","Parity","StopBits","XonChar","XoffChar","ErrorChar","EofChar","EvtChar","wReserved1");
    }
}
