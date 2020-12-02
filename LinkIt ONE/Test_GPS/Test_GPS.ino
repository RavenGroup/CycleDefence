#include <LGPS.h>
#include <LSD.h>
#include <LStorage.h>
#include "parse.h"

#define drv LSD

gpsSentenceInfoStruct info;

void setup() {
  // Serial.begin(115200);
  pinMode(13, OUTPUT);
  digitalWrite(13, LOW);
  LGPS.powerOn();
  drv.begin();
  LFile file = drv.open("data.txt", FILE_WRITE);
  char *data;
  // Serial.println("LGPS Power on, and waiting ...");
  // delay(3000);
  for (byte i = 0; i < 8 * 60; i++) {
    // Serial.println("LGPS loop");
    LGPS.getData(&info);
    data = (char*)info.GPGGA;
    // Serial.println(data);
    printGPGGA((const char*)info.GPGGA);
    file.println(data);
    // Serial.print("\n\n\n");
    delay(60000);
  }
  file.close();
}

void loop() {
}
