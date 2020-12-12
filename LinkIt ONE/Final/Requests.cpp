#include "Requests.h"


void Request::begin() {
  LWiFi.begin();
}


byte Request::connect_to_wifi(char *wifi_ap, String wifi_password, int retries) {
  for (int iter = 0; iter < retries; iter++) {
    if (0 != LWiFi.connect(wifi_ap, LWiFiLoginInfo(LWIFI_WPA, wifi_password)))
      return 0;
    delay(1000);
  }
  return 1;
}


byte Request::connect_to_server(char *server_url, unsigned short port, int retries) {
  for (int iter = 0; iter < retries; iter++) {
    if (0 != client.connect(server_url, 80))
      return 0;
    delay(1000);
  }
  return 1;
}


String Request::send_post(char *server_url, String data) {
  if (Serial)
    Serial.println("send HTTP POST request");
  
  client.println("POST /CDSystem HTTP/1.1");
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
  
  return response;
}
