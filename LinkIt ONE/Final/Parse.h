#ifndef Parse_h
#define Parse_h

#include <Arduino.h>

static unsigned char getComma(unsigned char, const char *);
static double getDoubleNumber(const char *);
static double getIntNumber(const char *);
void printGPGGA(const char *);

#endif
