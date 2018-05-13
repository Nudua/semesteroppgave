package com.groupname.framework.serial.unix;

import com.groupname.framework.util.LibraryUtils;
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
    private static final String DEFAULT_PORT = "/dev/ttyACM0";
    private static final int DEFAULT_BOUNDS = 13; //9600 bits per second

    private boolean open;
    private final String port;
    private final int bounds;

    // Native handle to the file (port)
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
        nativeLibrary = LibraryUtils.load(CLibraryUnix.NAME, CLibraryUnix.class);

        if(nativeLibrary == null) {
            throw new SerialPortException("The unix c library was not found.");
        }

        fd = nativeLibrary.open(port, FileOpenFlags.O_RDONLY | FileOpenFlags.O_NOCTTY | FileOpenFlags.O_SYNC);

        if(fd < 0) { // Unable to open the port
            throw new SerialPortException("Unable to open the specified port: " + port);
        }

        // Setup the port
        if(!setInterfaceAttributes(bounds)) {
            throw new SerialPortException("Unable to setup interface attributes for the SerialPort");
        }

        if(!setNonBlockingRead()) {
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

    private boolean setInterfaceAttributes(int speed) {
        assert fd > 0;

        Termios termios = new Termios();

        if(nativeLibrary.tcgetattr(fd, termios) < 0) {
            System.err.println("error in tcgetattr");
            return false;
        }

        // Set input / output speed
        nativeLibrary.cfsetospeed(termios, speed);
        nativeLibrary.cfsetispeed(termios, speed);

        // Use 8-bit chars
        termios.c_cflag = (termios.c_cflag & ~CSIZE) | CS8;

        // 0.6 seconds reading timeout
        termios.c_cc[VTIME] = 6;

        // No break processing
        termios.c_iflag &= ~IGNBRK;
        // No signalling chars
        termios.c_lflag = 0;

        termios.c_cflag |= 0;
        termios.c_cflag &= ~CSTOPB;
        termios.c_cflag &= ~CRTSCTS;

        // No remapping of characters
        termios.c_oflag = 0;

        // Turns off xon/xoff control characters
        termios.c_iflag &= ~(IXON | IXOFF | IXANY);

        // Reading does not block
        termios.c_cc[VMIN]  = 0;

        // Turn on reading
        termios.c_cflag |= (CLOCAL | CREAD);

        //Parity off
        termios.c_cflag &= ~(PARENB | PARODD);


        if(nativeLibrary.tcsetattr(fd, TCSANOW, termios) != 0) {
            System.err.println("error in tcsetattr");
            return false;
        }

        return true;
    }

    private boolean setNonBlockingRead() {
        assert fd > 0;

        Termios termios = new Termios();

        if(nativeLibrary.tcgetattr(fd, termios) != 0) {
            System.err.println("error in tcgetattr (blocking)");
            return false;
        }

        // Set to non-blocking read
        termios.c_cc[VMIN] = 0;
        // read timeout set to 0.6 seconds
        termios.c_cc[VTIME] = 6;

        if(nativeLibrary.tcsetattr(fd, TCSANOW, termios) != 0) {
            System.err.println("error in tcsetattr (blocking)");
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
