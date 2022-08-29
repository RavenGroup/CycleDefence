#include <LWiFi.h>
#include <LWiFiClient.h>


#define WIFI_AUTH LWIFI_WPA  // choose from LWIFI_OPEN, LWIFI_WPA, or LWIFI_WEP.


LWiFiClient c;
char wifi_ap[] = "D20";
char wifi_password[] = "81703838";
char url[] = "128.75.198.121";


void connect_to_wifi(char * wifi_ap, String wifi_password) {
  if (Serial)
    Serial.println("Connecting to AP");
  while (0 == LWiFi.connect(wifi_ap, LWiFiLoginInfo(WIFI_AUTH, wifi_password))) {
    delay(1000);
  }
  if (Serial)
      Serial.println("Done");
}


void connect_to_server(char *server_url, unsigned short port) {
  if (Serial)
    Serial.println("Connecting to WebSite");
  while (0 == c.connect(server_url, 80)) {
    if (Serial)
      Serial.println("Re-Connecting to WebSite");
    delay(1000);
  }
}


void send_post(char *data) {
  if (Serial)
    Serial.println("send HTTP POST request");
  c.println("POST / HTTP/1.1");
  char host[32];
  sprintf(host, "Host: %s", url);
  c.println(host);
  c.println("Connection: keep-alive");
  c.println("Content-Type: application/json");
  char data_len[32];
  sprintf(data_len, "Content-Length: %d", strlen(data));
  c.println(data_len);
  c.println();
  c.println(data);
  c.println();
  
  if (Serial)
    Serial.println("waiting HTTP response:");
  while (!c.available()) {
    if (Serial)
      Serial.print(".");
    delay(100);
  }
  if (Serial)
      Serial.println();
}


void print_response() {
  while (c) {
    int v = c.read();
    if (v != -1)
      Serial.print((char)v);
    else {
      Serial.println();
      if (Serial)
        Serial.println("no more content, disconnect");
      c.stop();
    }
  }
  Serial.println();
  Serial.println("disconnected by server");
}


void setup() {
  LWiFi.begin();
  Serial.begin(115200);

  while (!Serial) {
    delay(100);  
  }
  
  connect_to_wifi(wifi_ap, wifi_password);

  connect_to_server(url, 80);
  
  char json[] = "{\"id\":\"1\",\"username\":\"lev\",\"email\":\"blalba\"}";
  send_post(json);

  print_response();
}


void loop() {
  
}
