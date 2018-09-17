# Aliens in the backyard
This game was the final project (semesteroppgave) in the course [Programutvikling at OsloMet](http://www.hioa.no/Studier-og-kurs/TKD/Bachelor/Dataingenioer/Programplaner-for-tidligere-kull/Programplan-for-Bachelorstudium-i-ingenioerfag-data-2017/DATA1600-Programutvikling-2017) (in Norwegian) and was made by [Tor A. Ramstad](https://github.com/Nudua) and [Håkon Åreskjold](https://github.com/hakoares).  
It was written in Java using the JDK8 with JavaFX as it's GUI, the libraries JUnit 4.12 and JNA 4.5.1 were also used.

Official website: [http://aliensinthebackyard.com/](http://aliensinthebackyard.com/)  
Download: [Aliens.zip - Version 1.0](https://github.com/Nudua/semesteroppgave/releases/tag/1.0)

## Features
* Eight thrilling levels
* Stunning 2d graphics
* Cross-platform
* Keyboard support
* Gamepad support (XInput, Windows only)
* Level editor
* Custom controller support (Hitbox controller)

## Requires
Java 8 or above; Install either the [JRE from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html), or the [OpenJDK flavor](http://openjdk.java.net/install/).

It should then run on a supported Java platform.


## Building from source
* Install the [Java SE Development Kit 8u181](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
* Clone the repository with:
`git clone https://github.com/Nudua/semesteroppgave.git`
* Open in your favorite IDE (Intellij, eclipse etc.)  

Note: All the required libraries used by this project is located in the `/lib` folder.  
Note: Don't forget to mark the `/src` folder as source and `/test` folder as test.


## Technical details
### Some interesting interfaces / classes

#### SerialPort (interface)
This cross-platform interface is used to communicate with our custom Hitbox-controller built using an Arduino UNO and pushbuttons.  
Internally it uses the JNA (Java Native Access) to do serial communications on either Windows or Unix systems.

#### Usage
```java
try {
	// Get an instance of the SerialPort interface
	SerialPort serialPort = SerialPortFactory.create();
    
    // Opens the default port
	serialPort.open();
    
    // Buffer to hold our data
    byte[] buffer = new byte[256];

	// Read the amount requested into the buffer given
    long read = serialPort.read(buffer, buffer.length);
} catch (SerialPortException e) {
	System.err.println(e.getMessage());
}
```

## License
This project is licensed under the MIT License.
