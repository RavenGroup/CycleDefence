#include "Converter.h"


String parse2json(String prev, String key, String meaning, unsigned short t, unsigned short len) {
    if (t == len - 2)
        prev += "\"" + key + "\": \"" + meaning + "\"";
    else
        prev += "\"" + key + "\": \"" + meaning + "\",";
    return prev;
}


String distributor(String* info, unsigned short len) {
    String json = "";
    json += "{";
    for (unsigned short i = 0; i < len; i += 2)
        json = parse2json(json, info[i], info[i + 1], i, len);
    json += "}";
    return json;
}
