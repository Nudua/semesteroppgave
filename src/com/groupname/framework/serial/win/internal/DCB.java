package com.groupname.framework.serial.win.internal;

import com.sun.jna.Structure;
import static com.sun.jna.platform.win32.WinDef.*;

import java.util.Arrays;
import java.util.List;


/**
 * This class defines the control settings for serial port communications under the Win32 api.
 *
 * All the fields in this class have to be public because they are automatically mapped
 * and set by the JNA (Java Native Access) library to match the corresponding Win32 DCB structure.
 *
 * The names also match the native Win32 naming conventions and therefore ignore standard java naming conventions.
 */
public class DCB extends Structure {
    // The length of this structure, should be set with the super.size() method.
    public int DCBlength;
    // The baud rate of transfer for the serial port, in bits per seconds.
    public int BaudRate;
    // Set to 1 (TRUE in Win32) to enable binary transfer, must be set to true on Windows.
    public int fBinary;
    // If set to 1 (TRUE) parity checking will be done and errors reported.
    public int fParity;
    // CTS (clear to send) signal is monitored if set to 1.
    public int fOutxCtsFlow;
    // DTS (data set ready) signal is monitored if set to 1.
    public int fOutxDsrFlow;
    // DTR (data terminal ready) flow control, set to 0 to disable, 1 to enable and 2 to enable DTR handshaking.
    public int fDtrControl;
    // if set to 1 then the port is sensitive to the state of the DSR signal.
    public int fDsrSensitivity;
    // if set to 1
    public int fTXContinueOnXoff;
    // indicates if XON/XOFF flow control is used during sending.
    public int fOutX;
    // indicates if XON/XOFF flow control is used during receiving.
    public int fInX;
    // if set to 1 then parity errors will be replaced by the ErrorChar integer, fParity must also be set to 1.
    public int fErrorChar;
    // if set to 1, null bytes are discarded.
    public int fNull;
    // RTS (request to send) flow control, can be 0 (disable), 1 (enable), 2 (handshake), 3 (toggle)
    public int fRtsControl;
    // if set to 1 aborts any further reading if an error has occurred.
    public int fAbortOnError;
    // Reserved. Don't use
    public int fDummy2;
    // Reserved. Don't use
    public short wReserved;
    // The minimum amounts of bytes in use allowed before flow control is activated.
    public short XonLim;
    // The minimum amounts of bytes in use allowed before flow control is activated.
    public short XoffLim;
    // The number of bytes send and received.
    public byte ByteSize;
    // The parity type to use, 0 (none), 1 (odd), 2 (even), 3 (mark), 4 (space)
    public byte Parity;
    // Amount of stop bits to be used. Can be 0 (1 stop bit), 1 (1.5 stop bits), 2 (2 stop bits)
    public byte StopBits;
    // The char used for the XON during sending and receiving.
    public CHAR  XonChar;
    // The char used for the XOFF during sending and receiving.
    public CHAR  XoffChar;
    // The char used to replace bytes received with a parity error.
    public CHAR  ErrorChar;
    // The char used for signaling the end of data.
    public CHAR  EofChar;
    //The char used to indicate an event.
    public CHAR  EvtChar;
    // Reserved. Don't use
    public short wReserved1;

    /**
     * Gets all the fields in this structure in the order matching the Win32 native order of the DCB Structure.
     *
     * @return all the fields in this structure in the order matching the Win32 native order of the DCB Structure.
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("DCBlength", "BaudRate", "fBinary", "fParity", "fOutxCtsFlow",
                "fOutxDsrFlow", "fDtrControl","fDsrSensitivity","fTXContinueOnXoff","fOutX",
                "fInX","fErrorChar","fNull","fRtsControl","fAbortOnError","fDummy2","wReserved","XonLim","XoffLim",
                "ByteSize","Parity","StopBits","XonChar","XoffChar","ErrorChar","EofChar","EvtChar","wReserved1");
    }

    /**
     * Returns the String representation of this object.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "DCB{" +
                "DCBlength=" + DCBlength +
                ", BaudRate=" + BaudRate +
                ", fBinary=" + fBinary +
                ", fParity=" + fParity +
                ", fOutxCtsFlow=" + fOutxCtsFlow +
                ", fOutxDsrFlow=" + fOutxDsrFlow +
                ", fDtrControl=" + fDtrControl +
                ", fDsrSensitivity=" + fDsrSensitivity +
                ", fTXContinueOnXoff=" + fTXContinueOnXoff +
                ", fOutX=" + fOutX +
                ", fInX=" + fInX +
                ", fErrorChar=" + fErrorChar +
                ", fNull=" + fNull +
                ", fRtsControl=" + fRtsControl +
                ", fAbortOnError=" + fAbortOnError +
                ", fDummy2=" + fDummy2 +
                ", wReserved=" + wReserved +
                ", XonLim=" + XonLim +
                ", XoffLim=" + XoffLim +
                ", ByteSize=" + ByteSize +
                ", Parity=" + Parity +
                ", StopBits=" + StopBits +
                ", XonChar=" + XonChar +
                ", XoffChar=" + XoffChar +
                ", ErrorChar=" + ErrorChar +
                ", EofChar=" + EofChar +
                ", EvtChar=" + EvtChar +
                ", wReserved1=" + wReserved1 +
                '}';
    }
}
