#ifndef Requests_h
#define Requests_h

#include <Arduino.h>
#include <LWiFi.h>
#include <LWiFiClient.h>


class Request {
  public:
    void begin();
    void connect_to_wifi(char *, String);
    void connect_to_server(char *, unsigned short);
    String send_post(char *, String);
  private:
    LWiFiClient client;
};


#endif
