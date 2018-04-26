package com.groupname.framework.serial.unix;

import com.groupname.framework.serial.LibraryUtils;
import com.groupname.framework.serial.SerialPort;
import com.groupname.framework.serial.SerialPortException;
import com.groupname.framework.serial.unix.internal.CLibraryUnix;
import com.groupname.framework.serial.unix.internal.FileOpenFlags;
import com.groupname.framework.serial.unix.internal.Termios;
import com.groupname.framework.util.EmptyStringException;
import com.groupname.framework.util.Strings;

import static com.groupname.framework.serial.unix.internal.Termios.Constants.*;

import java.security.InvalidParameterException;


/**
 * An unix implementation of the SerialPort interface.
 *
 * The default port is /dev/ttyACM0 and bounds are 9600 bits per second.
 */
public class SerialPortUnix implements SerialPort {

    public static final String DEFAULT_PORT = "/dev/ttyACM0";
    public static final int DEFAULT_BOUNDS = 13; //9600 bits per second

    private boolean open;
    private final String port;
    private final int bounds;

    // native handle to the file (port)
    private int fd;
    private CLibraryUnix nativeLibrary;

    /**
     * Creates a new instance of the SerialPortUnix class with the default parameters for port and bounds.
     *
     * The default port on linux is "/dev/ttyACM0" and the default bounds setting is 9600 bits per second
     */
    public SerialPortUnix() {
        this(DEFAULT_PORT, DEFAULT_BOUNDS);
    }

    /**
     * Creates a new instance of the SerialPortUnix class with the specified port and bounds.
     *
     * @param port the serial port to use.
     * @param bounds the rate of bits per seconds.
     * @throws NullPointerException if port is null.
     * @throws EmptyStringException if port is equal to an empty String "".
     */
    public SerialPortUnix(String port, int bounds) {
        this.port = Strings.requireNonNullAndNotEmpty(port);
        this.bounds = bounds;
    }

    /**
     * Attempts to open the serial port so it is possible to read from it.
     *
     * @throws SerialPortException if there was an error while attempting to open the port.
     */
    @Override
    public void open() throws SerialPortException {
        nativeLibrary = LibraryUtils.loadDll("c", CLibraryUnix.class);

        if(nativeLibrary == null) {
            throw new SerialPortException("The unix c library was not found.");
        }

        fd = nativeLibrary.open(port, FileOpenFlags.O_RDONLY | FileOpenFlags.O_NOCTTY | FileOpenFlags.O_SYNC);

        if(fd < 0) { // Unable to open the port
            // Get the error from OS?
            throw new SerialPortException("Unable to open the specified port: " + port);
        }

        // Setup the port
        if(!setInterfaceAttributes(bounds, 0)) {
            throw new SerialPortException("Unable to setup interface attributes for the SerialPort");
        }

        if(!setBlocking(0)) {
            throw new SerialPortException("Unable to setup blocking attributes for the SerialPort");
        }

        open = true;
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

        nativeLibrary.close(fd);
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

        // Attempt to read from the serial port using the native read function
        return nativeLibrary.read(fd, buffer, numBytesToRead);
    }

    private boolean setInterfaceAttributes(int speed, int parity) {
        assert fd > 0;

        Termios termios = new Termios();

        if(nativeLibrary.tcgetattr(fd, termios) < 0) {
            System.out.println("error in tcgetattr");
            return false;
        }

        nativeLibrary.cfsetospeed(termios, speed);
        nativeLibrary.cfsetispeed(termios, speed);

        termios.c_cflag = (termios.c_cflag & ~CSIZE) | CS8;     // 8-bit chars
        // disable IGNBRK for mismatched speed tests; otherwise receive break
        // as \000 chars
        termios.c_iflag &= ~IGNBRK;         // disable break processing
        termios.c_lflag = 0;                // no signaling chars, no echo,
        // no canonical processing
        termios.c_oflag = 0;                // no remapping, no delays
        termios.c_cc[VMIN]  = 0;            // read doesn't block
        termios.c_cc[VTIME] = 5;            // 0.5 seconds read timeout

        termios.c_iflag &= ~(IXON | IXOFF | IXANY); // shut off xon/xoff ctrl

        termios.c_cflag |= (CLOCAL | CREAD);        // ignore modem controls,
        // enable reading
        termios.c_cflag &= ~(PARENB | PARODD);      // shut off parity
        termios.c_cflag |= parity;
        termios.c_cflag &= ~CSTOPB;
        termios.c_cflag &= ~CRTSCTS;

        if(nativeLibrary.tcsetattr(fd, TCSANOW, termios) != 0) {
            System.out.println("error in tcsetattr");
            return false;
        }

        return true;
    }

    private boolean setBlocking(int shouldBlock) {
        assert fd > 0;

        Termios termios = new Termios();

        if(nativeLibrary.tcgetattr(fd, termios) != 0) {
            System.out.println("error in tcgetattr (blocking)");
            return false;
        }

        termios.c_cc[VMIN] = shouldBlock == 1 ? (short)1 : (short)0;
        termios.c_cc[VTIME] = 5;        // 0.5 seconds read timeout

        if(nativeLibrary.tcsetattr(fd, TCSANOW, termios) != 0) {
            System.out.println("error in tcsetattr (blocking)");
            return false;
        }

        return true;
    }

    /**
     * Returns the String representation of this Object.
     * open, a boolean that says whether the port is open or not.
     * port, the current port used by this object.
     * bounds, the rate of transfer.
     * fd, is the internal fileHandle to the underlying port.
     * nativeLibrary, the nativeLibrary used by this class to do serialport communications.
     *
     * @return the String representation of this Object.
     */
    @Override
    public String toString() {
        return "SerialPortUnix{" +
                "open=" + open +
                ", port='" + port + '\'' +
                ", bounds=" + bounds +
                ", fd=" + fd +
                ", nativeLibrary=" + nativeLibrary +
                '}';
    }
}
