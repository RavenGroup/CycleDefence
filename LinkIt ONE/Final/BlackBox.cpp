#include "BlackBox.h"


#define drv LSD


void BlackBox::begin(void) {
  drv.begin();
}


void BlackBox::writeData(char *data) {
  LFile file = drv.open("data.txt", FILE_WRITE);
  if (file) {
    file.println(data);
    file.close();
  }
}
