#ifndef Requests_h
#define Requests_h

#include <Arduino.h>
#include <LWiFi.h>
#include <LWiFiClient.h>


class Request {
  public:
    void begin();
    byte connect_to_wifi(char *, String, int);
    byte connect_to_server(char *, unsigned short, int);
    String send_post(char *, String);
  private:
    LWiFiClient client;
};


#endif
