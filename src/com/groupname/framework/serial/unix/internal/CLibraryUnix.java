package com.groupname.framework.serial.unix.internal;

import com.sun.jna.Library;

public interface CLibraryUnix extends Library {

    /* Set the output baud rate stored in *TERMIOS_P to SPEED.  */
    //extern int cfsetospeed (struct termios *__termios_p, speed_t __speed) __THROW;

    /* Set the input baud rate stored in *TERMIOS_P to SPEED.  */
    //extern int cfsetispeed (struct termios *__termios_p, speed_t __speed) __THROW;

    /* Set the state of FD to *TERMIOS_P.
       Values for OPTIONAL_ACTIONS (TCSA*) are in <bits/termios.h>.  */
    //extern int tcsetattr (int __fd, int __optional_actions, const struct termios *__termios_p) __THROW;

    /* Put the state of FD into *TERMIOS_P.  */
    //extern int tcgetattr (int __fd, struct termios *__termios_p) __THROW;

    // Set the output baud rate stored in *TERMIOS_P to SPEED.
    int cfsetospeed(Termios termios, int speed);

    // Set the input baud rate stored in *TERMIOS_P to SPEED.
    int cfsetispeed(Termios termios, int speed);

    /* Put the state of FD into *TERMIOS_P.  */
    int tcgetattr(int fd, Termios termios);

    /* Set the state of FD to *TERMIOS_P.
       Values for OPTIONAL_ACTIONS (TCSA*) are in <bits/termios.h>.  */
    int tcsetattr(int fd, int optionalActions, Termios termios);

    /* Open FILE and return a new file descriptor for it, or -1 on error.
   OFLAG determines the type of access used.  If O_CREAT or O_TMPFILE is set
   in OFLAG, the third argument is taken as a `mode_t', the mode of the
   created file.

   This function is a cancellation point and therefore not marked with
   __THROW.  */
    //extern int open (const char *__file, int __oflag, ...) __nonnull ((1));

    int open(String fileName, int openFlags);

    /* Close the file descriptor FD.

   This function is a cancellation point and therefore not marked with
   __THROW.  */
    //extern int close (int __fd);
    int close(int fd);

    /* Read NBYTES into BUF from FD.  Return the
       number read, -1 for errors or 0 for EOF.

       This function is a cancellation point and therefore not marked with
       __THROW.  */
    //extern ssize_t read (int __fd, void *__buf, size_t __nbytes) __wur;
    long read(int fd, byte[] buff, int nbytes);

    /* Write N bytes of BUF to FD.  Return the number written, or -1.

       This function is a cancellation point and therefore not marked with
       __THROW.  */
    //extern ssize_t write (int __fd, const void *__buf, size_t __n) __wur;
    long write(int fd, byte[] buff, int nBytes);
}
