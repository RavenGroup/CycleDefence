#ifndef Accel_h
#define Accel_h

#include <Arduino.h>
#include "Wire.h"


class Accelerometer {
  public:
    void begin(void);
    int getDelta(void);
};

#endif
