#include <LGPS.h>
#include <LBattery.h>
#include <Wire.h>
#include "BlackBox.h"
#include "Converter.h"
#include "Requests.h"


BlackBox black_box;
gpsSentenceInfoStruct info;
Request req;
String data[16];
char wifi_ap[] = "D20";
char wifi_password[] = "81703838";
char url[] = "128.75.198.121";
char buff[256];
char lcd_buff[16];
byte have_inet_access = 0;
byte have_server_connection = 0;


void setup() {
  Serial.begin(115200);
  LGPS.powerOn();
  black_box.begin();
  req.begin();
  
  Serial.println("Connecting to AP");
  if (req.connect_to_wifi(wifi_ap, wifi_password, 3)) {
    Serial.println("Failed");
    have_inet_access = 0;
  } else {
    Serial.println("Success");
    have_inet_access = 1;
  }

  if (have_inet_access) {
    Serial.println("Connect to server");
    
    if (req.connect_to_server(url, 80, 1)) {
      have_server_connection = 0;
      Serial.println("Failed");
    } else {
      have_server_connection = 1;
      Serial.println("Success");
    }
  }
}


void loop() {
  LGPS.getData(&info);
  Serial.println("Getted data");

  data[0] = "id";
  data[1] = "2454";
  data[2] = "data";
  data[3] = String((char *)info.GPGGA);
  data[3].trim();
  data[4] = "userType";
  data[5] = "CDSystem";

  Serial.println("---------- Black Box ----------");
  if (!black_box.writeData((char *)info.GPGGA))
    Serial.println("Writed data");
  else
    Serial.println("Dont saved data");
  Serial.println("---------- End ----------\n");

  if (have_server_connection) {
    String res = "";
    res = req.send_post(url, distributor(data, 6));
    Serial.println("---------- Response ----------");
    Serial.println(res);
    Serial.println("---------- End ----------\n");
  }

  Serial.println("---------- Battery ----------");
  sprintf(buff, "Battery level = %d", LBattery.level() );
  Serial.println(buff);
  sprintf(buff, "is charging = %d", LBattery.isCharging() );
  Serial.println(buff);
  Serial.println("---------- End ----------\n");

  Serial.println("Waitting for 30 seconds");

  delay(30000);
}
