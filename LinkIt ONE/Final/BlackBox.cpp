#include "BlackBox.h"


#define drv LSD


void BlackBox::begin(void) {
  drv.begin();
}


byte BlackBox::writeData(char *data) {
  LFile file = drv.open("data.txt", FILE_WRITE);
  if (file) {
    file.println(data);
    file.close();
    return 0;
  } else
    return 1;
}
