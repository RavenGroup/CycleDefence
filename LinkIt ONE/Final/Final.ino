#include <LGPS.h>
#include <LBattery.h>
#include <Wire.h>
#include "BlackBox.h"
#include "Converter.h"
#include "Requests.h"
#include "GPRMCparser.h"


BlackBox black_box;
gpsSentenceInfoStruct info;
Request req;
String data[20];
char wifi_ap[] = "D20";
char wifi_password[] = "81703838";
char url[] = "128.75.198.121";
char buff[256];
char lcd_buff[16];
bool have_inet_access = 0;
bool have_server_connection = 0;
bool signaling = 0;
bool stealing = 0;


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
}


void loop() {
  // Connect to ap 
  if (!req.connected_wifi()) {
    Serial.println("Connecting to AP");
    if (req.connect_to_wifi(wifi_ap, wifi_password, 2)) {
      Serial.println("Failed");
      have_inet_access = 0;
    } else {
      Serial.println("Success");
      have_inet_access = 1;
    }
  }
  // End

  // Connect to serce
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
  
  LGPS.getData(&info);
  Serial.println("Getted data");
  Serial.println((char *)info.GPGSA);
  String parsed[15];
  GPRMC((char *)info.GPRMC, parsed);

  data[0] = "system_id";
  data[1] = "2454";
  data[2] = "time";
  data[3] = (String)parsed[1];
  data[4] = "latitude";
  data[5] = (String)parsed[3] + (String)parsed[4];
  data[6] = "longitude";
  data[7] = (String)parsed[5] + (String)parsed[6];
  data[8] = "date";
  data[9] = (String)parsed[9];
  data[10] = "temperature";
  data[11] = "0";
  data[12] = "battery";
  data[13] = (String)LBattery.level();
  data[14] = "accuracy";
  data[15] = (String)parsed[2];
  data[16] = "speed";
  data[17] = (String)parsed[7];
  
  if (signaling) {
    if (parsed[7].toFloat() > 1 or parsed[2] == "V") {
      stealing = 1;
    }
  } else
    stealing = 0;
  
  data[18] = "stealing";
  data[19] = (String)stealing;

  Serial.println(distributor(data, 20));
  

  Serial.println("---------- Black Box ----------");
  if (!black_box.writeData((char *)info.GPRMC))
    Serial.println("Writed data");
  else
    Serial.println("Dont saved data");
  Serial.println("---------- End ----------\n");

  if (have_server_connection) {
    char *res;
    res = req.send_post(url, distributor(data, 20), 50);
    if (atoi(res))
      signaling = 1;
    else
      signaling = 0;
    Serial.println("---------- Response ----------");
    Serial.println(atoi(res));
    Serial.println("---------- End ----------\n");
  }

  Serial.println("---------- Battery ----------");
  sprintf(buff, "Battery level = %d", LBattery.level() );
  Serial.println(buff);
  sprintf(buff, "is charging = %d", LBattery.isCharging() );
  Serial.println(buff);
  Serial.println("---------- End ----------\n");

  if (stealing)
    delay(100);
  else
    delay(10000);
}
