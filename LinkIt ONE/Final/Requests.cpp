#include "Requests.h"


void Request::begin() {
  LWiFi.begin();
}


void Request::connect_to_wifi(char *wifi_ap, String wifi_password) {
  if (Serial)
    Serial.println("Connecting to AP");
  while (0 == LWiFi.connect(wifi_ap, LWiFiLoginInfo(LWIFI_WPA, wifi_password))) {
    delay(1000);
  }
  if (Serial)
      Serial.println("Done");
}


void Request::connect_to_server(char *server_url, unsigned short port) {
  if (Serial)
    Serial.println("Connecting to WebSite");
  while (0 == client.connect(server_url, 80)) {
    if (Serial)
      Serial.println("Re-Connecting to WebSite");
    delay(1000);
  }
}


String Request::send_post(char *server_url, String data) {
  if (Serial)
    Serial.println("send HTTP POST request");
  
  client.println("POST / HTTP/1.1");
  char host[32];
  sprintf(host, "Host: %s", server_url);
  client.println(host);
  client.println("Connection: keep-alive");
  client.println("Content-Type: application/json");
  char data_len[32];
  sprintf(data_len, "Content-Length: %d", data.length());
  client.println(data_len);
  client.println();
  client.println(data);
  client.println();
  
  if (Serial)
    Serial.println("waiting HTTP response:");
  while (!client.available()) {
    if (Serial)
      Serial.print(".");
    delay(100);
  }
  
  if (Serial)
      Serial.println();
  
  String response = "";
  while (client) {
    int v = client.read();
    if (v != -1)
      response += (char)v;
    else {
      Serial.println();
      if (Serial)
        Serial.println("no more content, disconnect");
      client.stop();
    }
  }
  Serial.println();
  Serial.println("disconnected by server");
}
