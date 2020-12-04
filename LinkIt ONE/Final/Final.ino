#include <LGPS.h>
#include "BlackBox.h"


BlackBox black_box;
gpsSentenceInfoStruct info;


void setup() {
  LGPS.powerOn();
  black_box.begin();
}


void loop() {
  LGPS.getData(&info);
  black_box.writeData((char *)info.GPGGA);
  delay(10000);
}
