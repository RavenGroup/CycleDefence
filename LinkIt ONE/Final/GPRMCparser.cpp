#include "GPRMCparser.h"


void GPRMC(char *raw, String *out) {
    int j = 0;
    for (int i = 0; i < strlen(raw); i++) {
        if (raw[i] == ',')
            j++;
        else
            out[j] += raw[i];
    }
}
