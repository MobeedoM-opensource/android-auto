/*
  Smoothing

  Reads repeatedly from an analog input, calculating a running average and
  printing it to the computer. Keeps ten readings in an array and continually
  averages them.

  The circuit:
  - analog sensor (potentiometer will do) attached to analog input 0

  created 22 Apr 2007
  by David A. Mellis  <dam@mellis.org>
  modified 9 Apr 2012
  by Tom Igoe

  This example code is in the public domain.

  http://www.arduino.cc/en/Tutorial/Smoothing
*/

/*
   GPS: 250  1
   CALL+:  185  2
   CALL-:  60   2
   M:    125  2
   STOP: 312  2

   VOL+: 558  2
   VOL-: 613  1
   MUTE: 430  1
   NEXT:   375  1
   PREV:   495  1
*/

#include "HID-Project.h"

// Define the number of samples to keep track of. The higher the number, the
// more the readings will be smoothed, but the slower the output will respond to
// the input. Using a constant rather than a normal variable lets us use this
// value to determine the size of the readings array.
const int numReadings = 20;

int readings[numReadings];      // the readings from the analog input
int readIndex = 0;              // the index of the current reading
int total = 0;                  // the running total
int average = 0;                // the average

int readings2[numReadings];      // the readings from the analog input
int readIndex2 = 0;              // the index of the current reading
int total2 = 0;                  // the running total
int average2 = 0;                // the average

int localMin = 1000;
int localMin2 = 1000;

int inputPin = A2;
int inputPin2 = A3;

// GPS, CALL+, CALL-, M, STOP, VOL+, VOL-, MUTE, NEXT, PREV
int KEYS[10] = {225,170,57,113,283,510,555,395,340,465};
int BASE=650;
int tolerance = 25;
boolean skip = false;

// to detect longpress
int nLoop = 0;
int nLoop2 = 0;
int NLOOP_LONGPRESS = 60;

void setup() {
  // initialize serial communication with computer:
  Serial.begin(9600);
  // initialize all the readings to 0:
  for (int thisReading = 0; thisReading < numReadings; thisReading++) {
    readings[thisReading] = 0;
  }
  for (int thisReading = 0; thisReading < numReadings; thisReading++) {
    readings2[thisReading] = 0;
  }
}

void loop() {
  skip = false;
  // subtract the last reading:
  total = total - readings[readIndex];
  // read from the sensor:
  readings[readIndex] = analogRead(inputPin);
  // add the reading to the total:
  total = total + readings[readIndex];
  // advance to the next position in the array:
  readIndex = readIndex + 1;

  // if we're at the end of the array...
  if (readIndex >= numReadings) {
    // ...wrap around to the beginning:
    readIndex = 0;
  }

  // calculate the average:
  average = total / numReadings;

  if(average < BASE) {
    nLoop++;
    if(localMin > average)
       localMin = average;
  } else {
    if(localMin < 1000) {
      skip=true;
      for(int i=0; i<10; ++i) {
        int delta = KEYS[i] - localMin;
        if(abs(delta) < tolerance) {
          manageKey(i, nLoop);
          break;
        }
      }
      nLoop=0;
    }
    localMin = 1000;
  }

  // second channel
  // subtract the last reading:
  total2 = total2 - readings2[readIndex2];
  // read from the sensor:
  readings2[readIndex2] = analogRead(inputPin2);
  // add the reading to the total:
  total2 = total2 + readings2[readIndex2];
  // advance to the next position in the array:
  readIndex2 = readIndex2 + 1;

  // if we're at the end of the array...
  if (readIndex2 >= numReadings) {
    // ...wrap around to the beginning:
    readIndex2 = 0;
  }

  // calculate the average:
  average2 = total2 / numReadings;

  String out2 = "A3" + average2;
  if(average2 < BASE) {
    nLoop2++;
    if(localMin2 > average2)
       localMin2 = average2;
  } else {
    if(localMin2 < 1000 && !skip) {
//      Serial.println(localMin2);
      for(int i=0; i<10; ++i) {
        int delta = KEYS[i] - localMin2;
        if(abs(delta) < tolerance) {
          manageKey(i, nLoop2);
          break;
        }
      }
      nLoop2 = 0;
    }
    localMin2 = 1000;
  }
    
  delay(10);        // delay in between reads for stability
}

void manageKey(int key, int nloop) {
// GPS, CALL+, CALL-, M, STOP, VOL+, VOL-, MUTE, NEXT, PREV
    boolean singlePress = nloop < NLOOP_LONGPRESS;
    switch(key) {
      case 0:
       Keyboard.write((singlePress ? KEY_F1 : KEY_F5)); // KEYCODE_F1
      break;
      case 1:
       Keyboard.write((singlePress ? KEY_F2 : KEY_F6)); // 
      break;
      case 2:
       Keyboard.write((singlePress ? KEY_F3 : KEY_F7)); // 
      break;
      case 3:
       Keyboard.write((singlePress ? KEY_F4 : KEY_F8));// 
      break;
      case 4:
       Consumer.write((singlePress ? MEDIA_PLAY_PAUSE : KEY_F9));
       break;
      case 5:
       Consumer.write((singlePress ? MEDIA_VOL_UP : KEY_F10));
       break;
      case 6:
       Consumer.write((singlePress ? MEDIA_VOL_DOWN : KEY_F11));
       break;
      case 7:
       Consumer.write((singlePress ? MEDIA_VOL_MUTE : KEY_F12));
       break;
      case 8:
       if(singlePress)
        Consumer.write(MEDIA_NEXT);
       else
        Keyboard.write(KEY_RIGHT_SHIFT);// 
       break;
      case 9:
       if(singlePress)
        Consumer.write(MEDIA_PREV);
       else
        Keyboard.write(KEY_LEFT_SHIFT);// 
      break;
    }
}
