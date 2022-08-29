#include "Accelerometer.h"


const int MPU_addr = 0x68;
int16_t data_accel[7]; 
int last;


void getData() {
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr, 14, true);
  for (byte i = 0; i < 7; i++) {
    data_accel[i] = Wire.read() << 8 | Wire.read();
  }
}


void Accelerometer::begin() {
  Wire.begin();
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);
  Wire.write(0);
  Wire.endTransmission(true);

  getData();
  last = data_accel[0] + data_accel[1] + data_accel[2];
}


int Accelerometer::getDelta() {
  getData();  // получаем
  int delta = last - (data_accel[0] + data_accel[1] + data_accel[2]);
  last = data_accel[0] + data_accel[1] + data_accel[2];
  return delta;
}
