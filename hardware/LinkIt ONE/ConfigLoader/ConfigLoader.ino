#include "Wire.h"
const int MPU_addr = 0x68; // адрес датчика
// массив данных
// [accX, accY, accZ, temp, gyrX, gyrY, gyrZ]
// acc - ускорение, gyr - угловая скорость, temp - температура (raw)
int16_t data[7];
int last = 0;
void setup() {
  // инициализация
  Wire.begin();
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
  Wire.endTransmission(true);

  getData();
  last = data[0] + data[1] + data[2];
  
  Serial.begin(9600);
}
void loop() {
  getData();  // получаем
  Serial.println(last - (data[0] + data[1] + data[2]));
  last = data[0] + data[1] + data[2];
  Serial.println();
  delay(200);
}


void getData() {
  Wire.beginTransmission(MPU_addr);
  Wire.write(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)
  Wire.endTransmission(false);
  Wire.requestFrom(MPU_addr, 14, true); // request a total of 14 registers
  for (byte i = 0; i < 7; i++) {
    data[i] = Wire.read() << 8 | Wire.read();
  }
}
