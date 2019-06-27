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
    if(localMin > average)
       localMin = average;
  } else {
    if(localMin < 1000) {
      skip=true;
      for(int i=0; i<10; ++i) {
        int delta = KEYS[i] - localMin;
        if(abs(delta) < tolerance) {
          manageKey(i);
          break;
        }
      }
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
    if(localMin2 > average2)
       localMin2 = average2;
  } else {
    if(localMin2 < 1000 && !skip)
//      Serial.println(localMin2);
      for(int i=0; i<10; ++i) {
        int delta = KEYS[i] - localMin2;
        if(abs(delta) < tolerance) {
          manageKey(i);
          break;
        }
      }
    localMin2 = 1000;
  }
    
  delay(10);        // delay in between reads for stability
}

void manageKey(int key) {
// GPS, CALL+, CALL-, M, STOP, VOL+, VOL-, MUTE, NEXT, PREV
    switch(key) {
      case 0:
       Keyboard.write(KEY_F1); // KEYCODE_F1
      break;
      case 1:
       Keyboard.write(KEY_F2); // 
      break;
      case 2:
       Keyboard.write(KEY_F3); // 
      break;
      case 3:
       Keyboard.write(KEY_F4);// 
      break;
      case 4:
       Consumer.write(MEDIA_PLAY_PAUSE);
       break;
      case 5:
       Consumer.write(MEDIA_VOL_UP);
       break;
      case 6:
       Consumer.write(MEDIA_VOL_DOWN);
       break;
      case 7:
       Consumer.write(MEDIA_VOL_MUTE);
       break;
      case 8:
       Consumer.write(MEDIA_NEXT);
       break;
      case 9:
       Consumer.write(MEDIA_PREV);
      break;
    }
}
