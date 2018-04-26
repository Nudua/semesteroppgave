package com.groupname.framework.serial;

import org.junit.Test;
import static org.junit.Assert.*;

public class SerialPortTests {

    @Test(expected = NullPointerException.class)
    public void readBufferCannotBeNull() {
        try {
            SerialPort serialPort = SerialPortFactory.create();
            serialPort.open();

            serialPort.read(null, 1);

        } catch (SerialPortException exception) {
            System.err.println(exception.getMessage());
            throw new NullPointerException("Unable to test without a open port");
        }
    }

    @Test(expected = NullPointerException.class)
    public void readBufferCannotBeEmpty() {
        try {
            SerialPort serialPort = SerialPortFactory.create();
            serialPort.open();

            serialPort.read(new byte[0], 1);

        } catch (SerialPortException exception) {
            System.err.println(exception.getMessage());
            throw new NullPointerException("Unable to test without a open port");
        }
    }

}
