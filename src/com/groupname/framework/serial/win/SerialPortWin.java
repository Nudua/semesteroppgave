package com.groupname.framework.serial.win;

import com.groupname.framework.serial.LibraryUtils;
import com.groupname.framework.serial.SerialPort;
import com.groupname.framework.serial.SerialPortException;
import com.groupname.framework.serial.BaudRate;
import com.groupname.framework.serial.win.internal.DCB;
import com.groupname.framework.serial.win.internal.Kernel32Library;
import com.groupname.framework.util.EmptyStringException;
import com.groupname.framework.util.Strings;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;

import java.security.InvalidParameterException;

/**
 * An Win32 implementation of the SerialPort interface.
 *
 * The default port is "COM3" and bounds are 9600 bits per second.
 */
public class SerialPortWin implements SerialPort {

    public static final String DEFAULT_PORT = "COM3";
    public static final BaudRate DEFAULT_BAUDRATE = BaudRate.CBR_9600; //9600 bits per second

    private final long GENERIC_READ = 0x8000_0000L;
    //private final long GENERIC_WRITE = 0x4000_0000L;
    private final int OPEN_EXISTING = 3;

    private boolean open;
    private final String port;
    private final BaudRate baudRate;

    private Kernel32Library nativeLibrary;
    private WinNT.HANDLE handle;

    /**
     * Creates a new instance of the SerialPortWin class with the default parameters for port and bounds.
     *
     * The default port on Windows is "COM3" and the default baudrate is 9600 bits per second.
     */
    public SerialPortWin() {
        this(DEFAULT_PORT, DEFAULT_BAUDRATE);
    }

    /**
     * Creates a new instance of the SerialPortWin class with the specified port and bounds.
     *
     * @param port the serial port to use.
     * @param rate the rate of bits per seconds
     * @throws NullPointerException if port is null.
     * @throws EmptyStringException if port is equal to an empty String "".
     */
    public SerialPortWin(String port, BaudRate rate) {
        this.port = Strings.requireNonNullAndNotEmpty(port);
        this.baudRate = rate;
    }

    /**
     * Attempts to open the serial port so it is possible to read from it.
     *
     * @throws SerialPortException if there was an error while attempting to open the port.
     */
    @Override
    public void open() throws SerialPortException {
        nativeLibrary = LibraryUtils.loadDll("kernel32.dll", Kernel32Library.class);

        if(nativeLibrary == null) {
            throw new SerialPortException("The unix c library was not found.");
        }

        // Attempt to get an native handle to the COM3 serial port
        handle = nativeLibrary.CreateFile(
                "COM3",
                GENERIC_READ, //| GENERIC_WRITE,
                0,
                null,
                OPEN_EXISTING,
                0,
                null);

        if(handle == WinBase.INVALID_HANDLE_VALUE) {
            throw new SerialPortException("Unable to open " + port);
        }

        setupPort();

        open = true;
    }

    private void setupPort() throws SerialPortException {
        // The DCB structure is a class that contains information about the serial port
        // It's used to set and get information about the serial port.
        DCB dcb = new DCB();

        dcb.DCBlength = dcb.size();

        if(!nativeLibrary.GetCommState(handle, dcb)) {
            throw new SerialPortException("Error while getting information about the current serial port.");
        }

        if(!nativeLibrary.BuildCommDCB("baud=9600 parity=N data=8 stop=1", dcb)) {
            throw new SerialPortException("Error while building the DCB structure");
        }
        /*
        // Setup default variables for the COM port.
        dcb.BaudRate = baudRate.asInt();
        dcb.ByteSize = 8;
        dcb.StopBits = (byte)StopBits.ONESTOPBIT.asInt();
        dcb.Parity = (byte)Parity.NO.asInt();
        dcb.fBinary = 1;
        */
        

        if(!nativeLibrary.SetCommState(handle, dcb)) {
            throw new SerialPortException("Error while setting information about the current serial port.");
        }
    }

    /**
     * Attempts to close an already open serial port.
     *
     * @throws SerialPortException if a port was not open.
     */
    @Override
    public void close() throws SerialPortException {
        if(!open) {
            throw new SerialPortException("No port has been opened yet, call open() before using this method.");
        }

        nativeLibrary.CloseHandle(handle);

        open = false;
    }

    /**
     * Returns the state of the serial port.
     *
     * @return true if the port is open, false otherwise.
     */
    @Override
    public boolean isOpen() {
        return false;
    }

    /**
     * Reads from an open serial port.
     *
     * @param buffer the byte array to read the data into.
     * @param numBytesToRead the bytes to read from the serial port.
     * @return the amount of bytes actually read.
     * @throws InvalidParameterException if the buffer is null or the buffer's length is less than numBytesToRead.
     * @throws SerialPortException if the serial port was not opened. @hint call open() first.
     */
    @Override
    public long read(byte[] buffer, int numBytesToRead) throws SerialPortException {
        if(buffer == null || buffer.length < numBytesToRead) {
            throw new InvalidParameterException();
        }

        if(!open) {
            throw new SerialPortException("No port has been opened yet, call open() before using this method.");
        }

        IntByReference bytesActuallyRead = new IntByReference();

        if(nativeLibrary.ReadFile(handle, buffer, numBytesToRead, bytesActuallyRead, null)) {
            return bytesActuallyRead.getValue();
        } else { // Make this an IOException instead
            throw new SerialPortException("Unable to read data from the serial port.");
        }
    }

    /**
     * Returns the String representation of this object.
     * open, a boolean that says whether the port is open or not.
     * port, the current port used by this object.
     * baudRate, the rate of transfer.
     * nativeLibrary, the nativeLibrary used by this class to do serialport communications.
     * handle, is the internal fileHandle to the underlying serialport.
     *
     * @return the String representation of this object.
     */
    @Override
    public String toString() {
        return "SerialPortWin{" +
                "open=" + open +
                ", port='" + port + '\'' +
                ", baudRate=" + baudRate +
                ", nativeLibrary=" + nativeLibrary +
                ", handle=" + handle +
                '}';
    }
}