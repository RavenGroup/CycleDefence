void setup() {
  pinMode(3, OUTPUT);
  pinMode(A0, INPUT);
}


void loop() {
  analogWrite(3, analogRead(A0) / 4);
}
