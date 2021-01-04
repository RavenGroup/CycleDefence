#include "Requests.h"


char response[1024];


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


bool Request::connected_wifi() {
  return LWiFi.status() == LWIFI_STATUS_CONNECTED;
}


byte Request::connect_to_server(char *server_url, unsigned short port, int retries) {
  for (int iter = 0; iter < retries; iter++) {
    if (0 != client.connect(server_url, 80))
      return 0;
    delay(1000);
  }
  return 1;
}


char *Request::send_post(char *server_url, String data, byte retries) {
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

  byte j = 0;
  while (!client.available()) {
    if (j == retries) {
      Serial.println("Can not send request");
      return "";
      break;
    }
    if (Serial)
      Serial.print(".");
    delay(100);
    j++;
  }
  
  if (Serial)
      Serial.println();

  unsigned int i = 0;
  while (client) {
    int v = client.read();
    if (v != -1) {
      response[i] = (char)v;
      i++;
    }
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
