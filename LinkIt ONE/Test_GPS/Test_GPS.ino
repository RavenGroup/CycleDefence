#include <LGPS.h>
#include <LSD.h>
#include <LStorage.h>

#define drv LSD

gpsSentenceInfoStruct info;
char buff[256];

static unsigned char getComma(unsigned char num, const char *str) {
  unsigned char i, j = 0;
  int len = strlen(str);
  for (i = 0; i < len; i ++)
  {
    if (str[i] == ',')
      j++;
    if (j == num)
      return i + 1;
  }
  return 0;
}

static double getDoubleNumber(const char *s) {
  char buf[10];
  unsigned char i;
  double rev;

  i = getComma(1, s);
  i = i - 1;
  strncpy(buf, s, i);
  buf[i] = 0;
  rev = atof(buf);
  return rev;
}

static double getIntNumber(const char *s) {
  char buf[10];
  unsigned char i;
  double rev;

  i = getComma(1, s);
  i = i - 1;
  strncpy(buf, s, i);
  buf[i] = 0;
  rev = atoi(buf);
  return rev;
}

int parseGPGGA(const char* GPGGAstr) {
  double latitude;
  double longitude;
  int tmp, hour, minute, second, num ;
  if (GPGGAstr[0] == '$')
  {
    tmp = getComma(1, GPGGAstr);
    hour     = (GPGGAstr[tmp + 0] - '0') * 10 + (GPGGAstr[tmp + 1] - '0');
    minute   = (GPGGAstr[tmp + 2] - '0') * 10 + (GPGGAstr[tmp + 3] - '0');
    second    = (GPGGAstr[tmp + 4] - '0') * 10 + (GPGGAstr[tmp + 5] - '0');

    sprintf(buff, "UTC timer %2d-%2d-%2d", hour, minute, second);
    Serial.println(buff);

    tmp = getComma(2, GPGGAstr);
    latitude = getDoubleNumber(&GPGGAstr[tmp]);
    tmp = getComma(4, GPGGAstr);
    longitude = getDoubleNumber(&GPGGAstr[tmp]);
    sprintf(buff, "latitude = %10.4f, longitude = %10.4f", latitude, longitude);
    Serial.println(buff);

    tmp = getComma(7, GPGGAstr);
    num = getIntNumber(&GPGGAstr[tmp]);
    sprintf(buff, "satellites number = %d", num);
    Serial.println(buff);
    return num;
  }
  else
  {
    Serial.println("Not get data");
    return -1;
  }
}

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
    if (parseGPGGA((const char*)info.GPGGA) > 0)
      digitalWrite(13, HIGH);
    file.println(data);
    // Serial.print("\n\n\n");
    delay(60000);
  }
  file.close();
}

void loop() {
}
