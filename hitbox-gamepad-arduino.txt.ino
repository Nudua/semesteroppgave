const int PIN_COUNT = 10;
const int START_PIN = 2;

/* Overview over our buttons and their bitwise value
  // Joystick
  Pin 2:    Up = 0x01
  Pin 3:    Down = 0x02
  Pin 4:    Left = 0x04
  Pin 5:    Right = 0x08

  // Buttons
  Pin 6:    Start = 0x10
  Pin 7:    Select = 0x20
  
  Pin 8:    ShootUp = 0x40
  Pin 9:    ShootDown = 0x80
  Pin 10:   ShootLeft = 0x100
  Pin 11:   ShootRight = 0x200
*/

void setup() {
  activatePins();
  Serial.begin(9600);
}

// Set all our digital pins into the INPUT mode
// Required for reading all the buttons
void activatePins() {
  for (auto i = 0; i < PIN_COUNT; i++) {
    auto pin = START_PIN + i;
    pinMode(pin, INPUT);
  }
}

void loop() {
  byte buttonState[3];
  
  readPins(buttonState);

  // Write our currentState over the serial port
  Serial.write(buttonState, 3);
}

// This method will read all our active pins and shift the result into a single short
void readPins(byte buttonState[]) {

  unsigned short state = 0;

  for (auto i = 0; i < PIN_COUNT; i++) {

    auto pin = START_PIN + i;

    int pinState = digitalRead(pin);
    // If current is going through the pin, then we're pressing a button
    if (pinState == HIGH) {
      // offset something
      state |= (1 << i);
    }
  }

  // Command byte, marks the beginning of our state
  buttonState[0] = 0xFF;
  // The first 8 bits of the button state
  buttonState[1] = (state & 0xFF);
  // The last 2 bits of the button state, stil sending it as a byte
  buttonState[2] = ((state >> 8) & 0xFF);
}




