#ifndef BlacBox_h
#define BlacBox_h

#include <Arduino.h>
#include <LStorage.h>
#include <LSD.h>


class BlackBox {
  public:
    void begin(void);
    void writeData(char * data);
};

#endif
