#include <LGPS.h>
#include "BlackBox.h"
#include "Converter.h"
#include "Requests.h"


#define AP "noname"
#define PASS "28733e4aee3e"


BlackBox black_box;
gpsSentenceInfoStruct info;
Request req;
String data[16];
char wifi_ap[] = "D20";
char wifi_password[] = "81703838";
char url[] = "128.75.198.121";


void setup() {
  LGPS.powerOn();
  black_box.begin();
  req.begin();
  Serial.begin(115200);

  req.connect_to_wifi(wifi_ap, wifi_password);
  req.connect_to_server(url, 80);
  
  data[0] = "id";
  data[1] = "2454";
  
  String response = req.send_post(url, distributor(data, 2));
  Serial.println(response);
}


void loop() {
}
