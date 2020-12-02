#include "LTask.h" 
#include "LWiFi.h" 
#include "LWiFiClient.h"
#include "string.h"

#define WIFI_AP "name_of_wifi"
#define WIFI_PASSWORD "passcode" 
#define WIFI_AUTH LWIFI_WPA  // choose from LWIFI_OPEN, LWIFI_WPA, or LWIFI_WEP. 
#define SITE_URL "google.com" 

LWiFiClient c;
boolean disconnectedMsg = false;
//String mty = ""; 
  
void setup() 
{
  LWiFi.begin(); 
  Serial.begin(115200);
  Serial.println("Connecting to AP"); 
  while (0 == LWiFi.connect(WIFI_AP, LWiFiLoginInfo(WIFI_AUTH, WIFI_PASSWORD))) 
  { 
    delay(1000); 
  } 
  Serial.println("Connecting to website");
  while (0 == c.connect(SITE_URL, 80))
  {
    Serial.println("Re-Connecting to website");
    delay(1000);
  } 
  Serial.println("send HTTP GET request");
  c.println("GET / HTTP/1.1");
//  mty = to_string(c);
  c.println("Host: " SITE_URL);
  c.println("Connection: close");
  c.println();
//  Serial.println(c);
}

void loop() {
  while (c)
  {
    int v = c.read();
    if (v != -1)
    {
      Serial.print((char)v);
    }  else {
      Serial.println("no more content, disconnect");
      c.stop();
      while (1)
      {
        delay(1);
      }
    }
  if (!disconnectedMsg)
  {
    Serial.println("disconnected by server");
    disconnectedMsg = true;
  }
  delay(500);
  }
}











































//#include <LWiFi.h>
//
//int i = 0;
//
//void setup() {
//  LWiFi.begin();
//  Serial.begin(9600);
//  Serial.println("Scan");    
//}
//
//void loop() {
//  Serial.println(i++);
//  listNetworks();
//  delay(3000);
//    
//}
//
//void listNetworks() {
//  // scan for nearby networks:
//  Serial.println("** Scan Networks **");
//  int numSsid = LWiFi.scanNetworks();
//  if (numSsid == -1) {
//    Serial.println("Couldn't get a wifi connection");
//    while (true);
//  }
//
//  // print the list of networks seen:
//  Serial.print("number of available networks:");
//  Serial.println(numSsid);
//
//  // print the network number and name for each network found:
//  for (int thisNet = 0; thisNet < numSsid; thisNet++) {
//    Serial.print(thisNet);
//    Serial.print(") ");
//    Serial.print(LWiFi.SSID(thisNet));
//    Serial.print("\tSignal: ");
//    Serial.print(LWiFi.RSSI(thisNet));
//    Serial.print(" dBm");
//  }
//}

//
//#include <SPI.h>
//#include <WiFi.h>
//
//void setup() {
//  //Initialize serial and wait for port to open:
//  Serial.begin(9600);
//  while (!Serial) {
//    ; // wait for serial port to connect. Needed for native USB port only
//  }
//
//  String fv = WiFi.firmwareVersion();
//  if (fv != "1.1.0") {
//    Serial.println("Please upgrade the firmware");
//  }
//}
//
//void loop() {
//  // scan for existing networks:
//  
//  Serial.println("Scanning available networks...");
//  listNetworks();
//  
//  delay(10000);
//}
//
